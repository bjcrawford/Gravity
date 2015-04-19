package edu.temple.cis3238.gravity.gravity.model.graphics2d.graphicsentity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author Brett Crawford
 * @version 1.0b last modified 3/25/2015
 */
public class GraphicsEntity {

    /**The unique identifier of the entity.*/
    protected int id;

    /**The name of the entity (testing purposes only).*/
    protected String name;

    /**An ArrayList of the image resource ids representing all possible orientations.*/
    protected ArrayList<String> imgResIds;

    /**
     * A constructor for entity to
     * @param name The name of the entity.
     * @param id The id of the entity.
     * */
    public GraphicsEntity(String name, int id){
        this.name = name;
        this.id = id;
    }
    /**
     * The default constructor for this class. Fields are populated using a JSON
     * representation of the object.
     * @param entityJSONObject The Entity JSONObject
     */
    public GraphicsEntity(JSONObject entityJSONObject) {
        this.imgResIds = new ArrayList<String>();
        try {
            this.id = entityJSONObject.getInt("id");
            this.name = entityJSONObject.getString("name");

            // This is definitely not the most elegant way to handle this
            for (int i = 0; i < 12; i++) {
                this.imgResIds.add(i, entityJSONObject.getString("img_res_id" + i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a JSON representation of this object.
     * @return The Entity JSONObject
     */
    public JSONObject toJSON() {

        JSONObject entityJSONObject = new JSONObject();
        try {
            entityJSONObject.put("id", this.getId());
            entityJSONObject.put("name", this.getName());

            // This is definitely not the most elegant way to handle this
            for (int i = 0; i < 12; i++) {
                entityJSONObject.put("img_res_id" + i, imgResIds.get(i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return entityJSONObject;
    }

    /**
     * Returns the ID of this entity.
     * @return The ID of this entity.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of this entity.
     * @return The name of this entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the Entity's
     * given orientation.
     * @return The image resource ID.
     */
    public String getImgResId(int orientation) {
        return this.imgResIds.get(orientation);
    }
}
