package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import java.util.List;
import android.graphics.Point;

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

    /**The orientation of the object, defined by the entity's trajectory i.e acceleration unit vector*/
    protected int orientation;

// Constructors ------------------------------------------------------------------------------------

// General Public Functions ------------------------------------------------------------------------

// General Protected Functions ---------------------------------------------------------------------

    /**
     * Apply acceleration to the entity by manipulating its d2x and d2y fields.
     * @param deltaT The time that has elapsed since the last call to the entity's update method.
     * @return A boolean expressing weather the objects state has been changed by this call.
     */
    abstract public boolean update(float deltaT);

    /**
     * Update the entity's orientation and transform the occupied space of the entity based on the change in orientation.
     * @param deltaTheta The change in orientation. This absolute value of this parameter should not exceed 360 i.e -360 <= deltaTheta <=360.
     *                      <br>If the deltaTheta falls outside of this range, the value (deltaTheta % 360 wil be used).
     */
    protected void rotate(int deltaTheta) {
        deltaTheta = deltaTheta % 360;
        this.orientation += deltaTheta;
        //TODO: Translate occupied space based on change in orientation.
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
}
