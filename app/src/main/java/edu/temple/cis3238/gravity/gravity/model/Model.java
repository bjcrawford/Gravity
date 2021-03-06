package edu.temple.cis3238.gravity.gravity.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.activity.PlayGameActivity;
import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;
import edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece.GamePiece;
import edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece.Objective;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.Graphics2D;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.graphicsentity.GraphicsEntity;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.Physics2D;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.PhysicsEntity;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 4/6/2015
 */
public class Model {

    private static final String TAG = "Model";

// Fields ------------------------------------------------------------------------------------------

    /**The physics component of the game model.*/
    private Physics2D physModel;

    /**The graphics component of the game model.*/
    private Graphics2D graphModel;

    /**The game state component of the game model.*/
    private GameState gameStateModel;

    /**A scalar defining the relative sizes of the physics and graphics models.<br>
     * i.e graphics position = physics position * physToGraphicsScalar*/
    private double physToGraphicsScalar;

    /**The id of the player graphics/ physics/ game-state entities*/
    private int playerID;

// Constructors ------------------------------------------------------------------------------------

    /**
     * Constructs a model object from a json representation. The setGravConstant() method
     * must be called following construction and prior to using the model.
     * @param selfAsJson
     */
    public Model(JSONObject selfAsJson) {
        try {
            int width = selfAsJson.getInt("width") / PlayGameActivity.PIXELS_PER_PHYSICS_GRID;
            int height = selfAsJson.getInt("height") / PlayGameActivity.PIXELS_PER_PHYSICS_GRID;
            this.physModel = new Physics2D(selfAsJson.getJSONObject("physics"), width, height);
            this.graphModel = new Graphics2D(selfAsJson.getJSONObject("graphics"));
            this.gameStateModel = new GameState(selfAsJson.getJSONObject("gamestate"));
        }catch(JSONException e) {
            e.printStackTrace();
        }

    }
// Private -----------------------------------------------------------------------------------------

// Protected ---------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    public void update(float deltaT) {
        this.physModel.update(deltaT);

        // Changed to check for zero life span rather than a null reference
        if(this.physModel.getEntity(this.playerID).getLifespan() != 0) {
            this.gameStateModel.updateGameState(this.physModel.getEntity(this.playerID).getPosition());
        }else {
            Log.d(TAG, "Player has zero lifespan");
            this.gameStateModel.setPlayable(false);
        }
    }

    /**
     * Get a map of the stationary in-game entities.
     * @return A list of the relative positions of the landmark physics entities.
     */
    public List<ImageResourceWrapper> getMap() {
        // Declare a new list to hold the rendering info for the physics objects.
        List<ImageResourceWrapper> rtrnResources = new ArrayList<>();
        for(PhysicsEntity physicsEntity : this.physModel.getEntities()) {
            // Get the graphics entity corresponding to the current physics entity.
            GraphicsEntity graphicsEntity = this.graphModel.getEntityByID(physicsEntity.getId());
            String imgResource = graphicsEntity.getImgResId(physicsEntity.getOrientation());
            rtrnResources.add(new ImageResourceWrapper(new Point(physicsEntity.getPosition().x, physicsEntity.getPosition().y), imgResource));
        }
        for(Objective objective : this.gameStateModel.getObjectives()) {
            GraphicsEntity graphicsEntity = this.graphModel.getEntityByID(objective.getId());
            // TODO: Orientation for objectives
            String imgResource = graphicsEntity.getImgResId(0);
            rtrnResources.add(new ImageResourceWrapper(new Point(objective.getPosition().x, objective.getPosition().y), imgResource));
        }
        return rtrnResources;
    }

    /**
     * Get the positions of a subset of in-game entities.<br>
     *     Positions will be given as 2D integer points, corresponding directly to in screen positions.
     * @param xDiff The limit of observation in the x direction;<br>
     *              i.e range = [0, xDiff]<br>
     *              intended: The width of the display screen.
     * @param yDiff The limit of observation in the y direction;<br>
     *              i.e domain = [0, yDiff]<br>
     *              intended: The height of the display screen.
     * @return A list of entities which appear (wholly, or partially) in the given frame
     */
    public List<ImageResourceWrapper> getFrame(int xDiff, int yDiff) {

        // Get the players position.
        Point center = this.physModel.getEntity(this.playerID).getPosition();
        //Log.d("Model", "Player Position: x: " + center.x + " y: " + center.y);
        // Get a list of entities surrounding the player.
        List<PhysicsEntity> physEnts =
                this.physModel.observe(center, (int)(xDiff / 2), (int)(yDiff / 2));

        // Declare a new list to hold the rendering info for the physics objects.
        List<ImageResourceWrapper> rtrnResources = new ArrayList<>();
        // Package the rendering resources of the entities.
        for(PhysicsEntity physicsEntity : physEnts) {
            // Get the graphics entity corresponding to the current physics entity.
            GraphicsEntity graphicsEntity = this.graphModel.getEntityByID(physicsEntity.getId());
            int graphX = physicsEntity.getPosition().x - center.x;
            int graphY = physicsEntity.getPosition().y - center.y;
            //TODO: remove debug statement
            //System.out.println("Entity: " + physEntity.getId() + " graphX: " + graphX + " graphY: " + graphY);
            String imgResource = graphicsEntity.getImgResId(physicsEntity.getOrientation());
            rtrnResources.add(new ImageResourceWrapper(new Point(graphX, graphY), imgResource));
        }

        for(GamePiece gamePiece : this.gameStateModel.observe(center, (int)(xDiff / 2), (int)(yDiff / 2))) {
            GraphicsEntity graphicsEntity = this.graphModel.getEntityByID(gamePiece.getId());
            int graphX = gamePiece.getPosition().x - center.x;
            int graphY = gamePiece.getPosition().y - center.y;
            String imgResource = graphicsEntity.getImgResId(0);
            rtrnResources.add(new ImageResourceWrapper(new Point(graphX, graphY), imgResource));
        }

        return rtrnResources;
    }

    public void receiveInput(float delta_d2x, float delta_d2y) {
        //Log.i(TAG, "Received input: delta_d2x: " + delta_d2x + ", delta_d2y: " + delta_d2y);
        this.physModel.applyAcceleratingForceToBody(this.playerID, delta_d2x, delta_d2y);
    }

    public JSONObject toJSON() {
        JSONObject selfAsJSON = new JSONObject();
        try {
            selfAsJSON.put("physics", this.physModel.toJSON());
            selfAsJSON.put("graphics", this.graphModel.toJSON());
            selfAsJSON.put("gamestate", this.gameStateModel.toJSON());
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return selfAsJSON;
    }

// Getters and Setters -----------------------------------------------------------------------------

    /**
     * Sets the gravity constant in the physics 2d model.
     *
     * @param gravConstant
     */
    public void setGravConstant(float gravConstant) {
        physModel.setGravConstant(gravConstant);
    }

    /**
     * Get the dimensions of the universe.
     * @return The dimensions (x, y) of the universe.
     */
    public Point getUniverseDimensions(){
        return this.physModel.getUniverseDimensions();
    }

    /**
     * Gets the game state component of the game model.
     * @return The game state component object
     */
    public GameState getGameStateModel() {
        return this.gameStateModel;
    }

}
