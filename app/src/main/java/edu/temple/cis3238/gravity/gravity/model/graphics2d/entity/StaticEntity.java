package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/23/2015
 */
public class StaticEntity extends Entity {

    /**
     * Constructs a StaticEntity object from a given ID and JSONObject.
     * @param id The entity ID.
     * @param staticEntityJSONObject The JSONObject representation of the StaticEntity.
     */
    public StaticEntity(int id, JSONObject staticEntityJSONObject) {
        super(id, staticEntityJSONObject);
        try {
            this.imgResIds.add(0, staticEntityJSONObject.getString("img_res_id0"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a JSONObject representation of the StaticEntity object.
     * @return The JSONObject.
     */
    public JSONObject getStaticEntityJSONObject() {
        JSONObject staticEntityJSONObject = super.getEntityJSONObject();
        try {
            staticEntityJSONObject.put("img_res_id0", this.getImgResId());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return staticEntityJSONObject;
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the StaticEntity.
     * @return The image resource ID.
     */
    public String getImgResId() {
        return this.imgResIds.get(0);
    }
}
