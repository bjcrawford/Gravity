package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/23/2015
 */
public class FixedEntity extends Entity {

    /**
     * Constructs a FixedEntity object from a given ID and JSONObject.
     * @param id The entity ID.
     * @param fixedEntityJSONObject The JSONObject representation of the FixedEntity.
     */
    public FixedEntity(int id, JSONObject fixedEntityJSONObject) {
        super(id, fixedEntityJSONObject);
        try {
            this.imgResIds.add(0, fixedEntityJSONObject.getString("img_res_id0"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a JSONObject representation of the FixedEntity object.
     * @return The JSONObject.
     */
    public JSONObject getFixedEntityJSONObject() {
        JSONObject fixedEntityJSONObject = super.getEntityJSONObject();
        try {
            fixedEntityJSONObject.put("img_res_id0", this.getImgResId());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return fixedEntityJSONObject;
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the FixedEntity.
     * @return The image resource ID.
     */
    public String getImgResId() {
        return this.imgResIds.get(0);
    }
}
