package edu.temple.cis3238.gravity.gravity.model.graphics2d.entity;

import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/23/2015
 */
public abstract class Entity {

    /**The unique identifier of the entity.*/
    protected int id;

    /**The x,y coordinates of the entity.*/
    protected Point position;

    /**The orientation of the object.*/
    protected int orientation;

    /**An ArrayList of the image resource ids representing all possible orientations.*/
    protected ArrayList<String> imgResIds;

    public Entity(int id, JSONObject staticEntityJSONObject) {
        this.id = id;
        this.position = new Point();
        this.orientation = 0;
        this.imgResIds = new ArrayList<String>();
        try {
            this.setX(staticEntityJSONObject.getInt("x0"));
            this.setY(staticEntityJSONObject.getInt("y0"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getEntityJSONObject() {

        JSONObject entityJSONObject = new JSONObject();
        try {
            entityJSONObject.put("id", this.getId());
            entityJSONObject.put("x0", this.getX());
            entityJSONObject.put("y0", this.getY());
            entityJSONObject.put("orientation", this.getOrientation());
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
}
