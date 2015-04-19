package edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece;

import org.json.JSONArray;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/31/2015
 */
public class Player extends GamePiece {

// Fields ------------------------------------------------------------------------------------------

// Constructors ------------------------------------------------------------------------------------

    public Player(Point position, int proximity) {
        this.position = new Point(position);
        this.proximity = proximity;
    }

    public Player(JSONArray selfAsJSON) {

    }

// Private -----------------------------------------------------------------------------------------



// Protected ---------------------------------------------------------------------------------------



// Public ------------------------------------------------------------------------------------------



// Getters and Setters -----------------------------------------------------------------------------

}
