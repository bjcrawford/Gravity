package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/23/2015
 */
public class DynamicEntity extends Entity {

    /**
     * Constructs a DynamicEntity object from a given ID and JSONObject.
     * @param id The entity ID.
     * @param dynamicEntityJSONObject The JSONObject representation of the StaticEntity.
     */
    public DynamicEntity(int id, JSONObject dynamicEntityJSONObject) {
        super(id, dynamicEntityJSONObject);
        try {
            // This is definitely not the most elegant way to handle this
            for (int i = 0; i < 12; i++) {
                this.imgResIds.add(i, dynamicEntityJSONObject.getString("img_res_id" + i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a JSONObject representation of the DynamicEntity object.
     * @return The JSONObject.
     */
    public JSONObject getStaticEntityJSONObject() {
        JSONObject staticEntityJSONObject = super.getEntityJSONObject();
        try {
            // This is definitely not the most elegant way to handle this
            for (int i = 0; i < 12; i++) {
                staticEntityJSONObject.put("img_res_id" + i, this.getImgResId(i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return staticEntityJSONObject;
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the DynamicEntities
     * current orientation.
     * @return The image resource ID.
     */
    public String getImgResId() {
        return this.imgResIds.get(this.getOrientation());
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the DynamicEntities
     * given orientation.
     * @return The image resource ID.
     */
    public String getImgResId(int orientation) {
        return this.imgResIds.get(orientation);
    }
}
