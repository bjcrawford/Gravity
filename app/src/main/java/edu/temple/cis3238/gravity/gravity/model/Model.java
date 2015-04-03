package edu.temple.cis3238.gravity.gravity.model;

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
 * @version 1.0a last modified 4/3/2015
 */
public class Model {

// Fields ------------------------------------------------------------------------------------------

    /**The physics component of the game model.*/
    private Physics2D physModel;

    /**The graphics component of the game model.*/
    private Graphics2D graphModel;

    /**The game state component of the game model.*/
    private GameState gameStateModel;

// Constructors ------------------------------------------------------------------------------------

    public Model() {

    }

    public Model(JSONObject selfAsJson) {

    }
// Private -----------------------------------------------------------------------------------------

// Protected ---------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    /**
     * Get a map of the stationary in-game entities.
     * @return A list of the relative positions of the landmark physics entities.
     */
    public List<Entity> getMap() {
        //TODO: get a list of the landmarks. Should be generated once and stored to prevent redundant acquisitions
        for(Landmark landmark : this.physModel.getLandmarks()) {

        }
        return new ArrayList<>();
    }

    /**
     * Get the positions of a subset of in-game entities.
     * @param frameCenter The point around which the frame will be constructed.
     * @param xDiff The limit of observation in the x direction;<br>
     *              i.e range = [frameCenter.x - xDiff, frameCenter.x + xDiff]
     * @param yDiff he limit of observation in the y direction;<br>
     *              i.e domain = [frameCenter.y - yDiff, frameCenter.y + yDiff]
     * @return A list of entities which appear (wholly, or partially) in the given frame
     */
    public List<Entity> getFrame(Point frameCenter, int xDiff, int yDiff) {
        //TODO: Get entities within the range, with position relative to the width/ height of the frame
        return new ArrayList<>();
    }

// Getters and Setters -----------------------------------------------------------------------------

}
