package edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece;

import org.json.JSONArray;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/31/2015
 */
public abstract class GamePiece {
    // Fields ------------------------------------------------------------------------------------------

    /**The radial threshold for activating the player.*/
    protected int proximity;

    /**The <x, y> location of the player.*/
    protected Point position;



// Constructors ------------------------------------------------------------------------------------

// Private -----------------------------------------------------------------------------------------



// Protected ---------------------------------------------------------------------------------------



// Public ------------------------------------------------------------------------------------------



// Getters and Setters -----------------------------------------------------------------------------

    public int getProximity() {
        return proximity;
    }

    public Point getPosition() {
        return position;
    }

}
