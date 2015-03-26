package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import java.util.List;
import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public abstract class Entity {

// Fields ------------------------------------------------------------------------------------------

    /**The unique identifier of the entity.*/
    protected int id;

    /**The time remaining until the entity expires.*/
    protected int lifespan;

    /**The x,y coordinates of the entity.*/
    protected Point position;

    /**A set of shapes of the object, used in rotation.*/
    protected List<List<Point>> shapes;

    /**A set of points describing the grid spaces occupied by the entity.*/
    protected List<Point> shape;

// Constructors ------------------------------------------------------------------------------------

// Private -----------------------------------------------------------------------------------------

// Protected ---------------------------------------------------------------------------------------

    /**
     * Update the entity's orientation and transform the occupied space of the entity based on the change in orientation.
     * @param orientationIndex The desired orientation of the entity in Z12.
     */
    protected void rotate(int orientationIndex) {
        orientationIndex = orientationIndex % this.shapes.size();
        this.shape = this.shapes.get(orientationIndex);
    }

// Public ------------------------------------------------------------------------------------------

    /**
     * Apply acceleration to the entity by manipulating its d2x and d2y fields.
     * @param deltaT The time that has elapsed since the last call to the entity's update method.
     */
    abstract public void update(float deltaT);

    public JSONObject toJSON() throws JSONException{
        JSONObject selfAsJSON = new JSONObject();
        selfAsJSON.put("id", this.id);
        selfAsJSON.put("x", this.position.x);
        selfAsJSON.put("y", this.position.y);

        JSONArray pShapes = new JSONArray();
        // For each shape in this entity's list of shapes...
        for(List<Point> subsection : this.shapes) {
            // For each point in the current shape...
            JSONArray pShape = new JSONArray();
            for(Point point : subsection) {
                // Create a new object to store the point.
                JSONObject pos = new JSONObject();
                pos.put("x", point.x);
                pos.put("y", point.y);
                pShape.put(pos);
            }
            pShapes.put(pShape);
        }

        selfAsJSON.put("shapes", pShapes);
        selfAsJSON.put("lifespan", this.lifespan);

        return selfAsJSON;
    }

// Getters and Setters -----------------------------------------------------------------------------

    /**
     * Get the list of grid spaces currently occupied by the entity.
     * @return A list of points occupied by the entity.
     */
    public List<Point> getShape() {
        return this.shape;
    }

    /**
     * Get the time remaining until this entity expires.
     * @return The remaining lifespan of this entity.
     */
    public int getLifespan() {
        return this.lifespan;
    }

    /**
     * Get the ID of this entity.
     * @return The ID of this entity.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the entity's position.
     * @return The point representation of the entity's position.
     */
    public Point getPosition() { return this.position; }

}
