package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Brett Crawford
 * @version 1.0b last modified 3/25/2015
 */
public class Entity {

    /**The unique identifier of the entity.*/
    protected int id;

    /**The name of the entity (testing purposes only).*/
    protected String name;

    /**The x,y coordinates of the entity.*/
    protected Point position;

    /**The orientation of the object.*/
    protected int orientation;

    /**An ArrayList of the image resource ids representing all possible orientations.*/
    protected ArrayList<String> imgResIds;

    /**
     * The default constructor for this class. Fields are populated using a JSON
     * representation of the object.
     * @param entityJSONObject The Entity JSONObject
     */
    public Entity(JSONObject entityJSONObject) {
        this.position = new Point();
        this.orientation = 0;
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
     * Returns the x position of this entity.
     * @return The x position.
     */
    public int getX() {
        return position.x;
    }

    /**
     * Sets the x position of this entity.
     * @param x The x position.
     */
    public void setX(int x) {
        this.position.x = x;
    }

    /**
     * Returns the y position of this entity.
     * @return The y position.
     */
    public int getY() {
        return position.y;
    }

    /**
     * Sets the y position of this entity.
     * @param y The y position.
     */
    public void setY(int y) {
        this.position.y = y;
    }

    /**
     * Returns the position (Point object) of this entity.
     * @return A Point object.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position (Point object) of this entity.
     * @param position A Point object.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Returns the orientation of this entity.
     * @return The orientation (0 - 11)
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Sets the orientation of this entity.
     * @param orientation The orientation (0 - 11)
     */
    public boolean setOrientation(int orientation) {
        if (orientation >= 0 && orientation <= 11) {
            this.orientation = orientation;

            return true;
        }

        return false;
    }

    /**
     * Returns the image resource ID (R.drawable.image_name) associated with the Entity's
     * current orientation.
     * @return The image resource ID.
     */
    public String getImgResId() {
        return this.imgResIds.get(this.getOrientation());
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
