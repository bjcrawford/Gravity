package edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/31/2015
 */
public class Objective extends GamePiece {

// Fields ------------------------------------------------------------------------------------------

// Constructors ------------------------------------------------------------------------------------

    public Objective(Point position, int proximity) {
        this.position = new Point(position);
        this.proximity = proximity;
    }

    public Objective(JSONArray selfAsJSON) {

    }

// Private -----------------------------------------------------------------------------------------



// Protected ---------------------------------------------------------------------------------------



// Public ------------------------------------------------------------------------------------------



// Getters and Setters -----------------------------------------------------------------------------
}
