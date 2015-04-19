package edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Ian M. Speers
 * @author Brett Crawford
 * @version 1.0a last modified 4/17/2015
 */
public class Event extends GamePiece {

// Fields ------------------------------------------------------------------------------------------

// Constructors ------------------------------------------------------------------------------------

    public Event(int id, Point position, int proximity) {
        this.id = id;
        this.position = new Point(position);
        this.proximity = proximity;
    }

    public Event(JSONObject selfAsJSON) {
        try {
            this.id = selfAsJSON.getInt("id");
            this.position = new Point(selfAsJSON.getInt("x0"), selfAsJSON.getInt("y0"));
            this.proximity = selfAsJSON.getInt("proximity");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

// Private -----------------------------------------------------------------------------------------



// Protected ---------------------------------------------------------------------------------------



// Public ------------------------------------------------------------------------------------------



// Getters and Setters -----------------------------------------------------------------------------
}
