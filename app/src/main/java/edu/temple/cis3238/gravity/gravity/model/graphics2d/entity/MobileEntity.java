package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/23/2015
 */
public class MobileEntity extends Entity {

    /**
     * Constructs a MobileEntity object from a given ID and JSONObject.
     * @param id The entity ID.
     * @param mobileEntityJSONObject The JSONObject representation of the MobileEntity.
     */
    public MobileEntity(int id, JSONObject mobileEntityJSONObject) {
        super(id, mobileEntityJSONObject);
        try {
            // This is definitely not the most elegant way to handle this
            for (int i = 0; i < 12; i++) {
                this.imgResIds.add(i, mobileEntityJSONObject.getString("img_res_id" + i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a JSONObject representation of the MobileEntity object.
     * @return The JSONObject.
     */
    public JSONObject getMobileEntityJSONObject() {
        JSONObject mobileEntityJSONObject = super.getEntityJSONObject();
        try {
            // This is definitely not the most elegant way to handle this
            for (int i = 0; i < 12; i++) {
                mobileEntityJSONObject.put("img_res_id" + i, this.getImgResId(i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return mobileEntityJSONObject;
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the MobileEntities
     * current orientation.
     * @return The image resource ID.
     */
    public String getImgResId() {
        return this.imgResIds.get(this.getOrientation());
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the MobileEntities
     * given orientation.
     * @return The image resource ID.
     */
    public String getImgResId(int orientation) {
        return this.imgResIds.get(orientation);
    }
}
