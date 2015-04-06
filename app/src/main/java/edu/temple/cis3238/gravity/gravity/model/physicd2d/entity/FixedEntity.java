package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
abstract public class FixedEntity extends Entity {

// Fields ------------------------------------------------------------------------------------------

    /**Defines the rate at which the landmark will spin*/
    protected int dTheta;

// Constructors ------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    /**
     * Returns a JSON object representation of the entity.
     * @return A JSON object containing the data of the entity.
     */
    public JSONObject toJSON() {

        JSONObject selfAsJSON = super.toJSON();
        try {
            selfAsJSON.put("dTheta", this.dTheta);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return selfAsJSON;
    }

// Protected ---------------------------------------------------------------------------------------

// Private -----------------------------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------
}
