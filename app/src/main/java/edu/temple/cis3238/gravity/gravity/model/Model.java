package edu.temple.cis3238.gravity.gravity.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.Graphics2D;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.entity.Entity;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.Physics2D;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Landmark;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 4/5/2015
 */
public class Model {

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

    public Model(JSONObject selfAsJson) {
        try {
            this.physModel = new Physics2D(selfAsJson.getJSONObject("physics"));
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
        if(this.physModel.getEntity(this.playerID).getPosition() != null) {
            this.gameStateModel.updateGameState(this.physModel.getEntity(this.playerID).getPosition());
        }else {
            this.gameStateModel.setPlayable(false);
        }

    }

    /**
     * Get a map of the stationary in-game entities.
     * @return A list of the relative positions of the landmark physics entities.
     */
    public List<ImageResourceWrapper> getMap() {
        //TODO: get a list of the landmarks. Should be generated once and stored to prevent redundant acquisitions
//        for(Landmark landmark : this.physModel.getLandmarks()) {
//
//        }
        return new ArrayList<>();
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
        // Get a list of entities surrounding the player.
        List<edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Entity> physEnts =
                this.physModel.observe(center, (int)((xDiff * this.physToGraphicsScalar) / 2), (int)((yDiff * this.physToGraphicsScalar)  / 2));

        // Declare a new list to hold the rendering info for the physics objects.
        List<ImageResourceWrapper> rtrnResources = new ArrayList<>();
        // Package the rendering resources of the entities.
        for(edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Entity physEntity : physEnts) {
            // Get the graphics entity corresponding to the current physics entity.
            edu.temple.cis3238.gravity.gravity.model.graphics2d.entity.Entity graphEntity
                    = this.graphModel.getEntityByID(physEntity.getId());
            int graphX = (int) (physEntity.getPosition().x * this.physToGraphicsScalar);
            int graphY = (int) (physEntity.getPosition().y * this.physToGraphicsScalar);
            String imgResource = graphEntity.getImgResId(physEntity.getOrientation());
            rtrnResources.add(new ImageResourceWrapper(new Point(graphX, graphY), imgResource));
        }

        return rtrnResources;
    }

// Getters and Setters -----------------------------------------------------------------------------

}
