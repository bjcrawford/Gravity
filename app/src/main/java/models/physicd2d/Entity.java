package models.physicd2d;

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
    private int id;

    /**A set of points describing the grid spaces occupied by the entity.*/
    private List<Point> occupiedSpace;

    /**The entity's current velocity in the x direction.*/
    private int dx;

    /**The entity's current velocity in the y direction.*/
    private int dy;

    /**The acceleration in the x direction currently being experienced by this entity.*/
    private float d2x;

    /**The acceleration in the y direction currently being experienced by this entity.*/
    private float d2y;

    /**The time remaining until the entity expires.*/
    private int lifespan;

    /**The orientation of the object*/
    private int orientation;

// Constructors ------------------------------------------------------------------------------------

// General Public Functions ------------------------------------------------------------------------

    /**
     * Apply acceleration to the entity by manipulating its d2x and d2y fields.
     * @param delta_d2x The value of acceleration in the x direction to be applied to the entity.
     * @param delta_d2y The value of acceleration in the y direction to be applied to the entity.
     * @param deltaT The time that has elapsed since the last call to the entity's update method.
     * @param deltaTheta The change in the entity's orientation.
     */
    abstract public void update(float delta_d2x, float delta_d2y, float deltaT, int deltaTheta);


// General Private Functions -----------------------------------------------------------------------

    /**
     * Modify the values of acceleration being experienced by the entity.
     * @param delta_d2x The value of acceleration in the x direction to be applied to the entity.
     * @param delta_d2y The value of acceleration in the y direction to be applied to the entity.
     */
    private void applyAcceleration(float delta_d2x, float delta_d2y) {
        this.d2x += delta_d2x;
        this.d2y += delta_d2y;
    }

    /**
     * Update the velocity of the entity based on its current acceleration, velocity, and the elapsed time given as input.
     * @param deltaT The time that has elapsed since the last call to the entity's accelerate method; used to calculate the entity's new velocity.
     */
    private void accelerate(float deltaT) {
        // Calculate the change in velocity experienced by the entity during the given interval:
        // delta_v = a * deltaT for the x and y components.
        this.dx += this.d2x * deltaT;
        this.dy += this.d2y * deltaT;
    }

    /**
     * Update the position of the entity based on its current acceleration, velocity, and the elapsed time given as input.
     * @param deltaT The time that has elapsed since the last call to the entity's accelerate method; used to calculate the entity's new velocity.
     */
    private void displace(float deltaT) {
        // Calculate the displacement experienced by the entity during the given interval:
        // delta_x = dx*deltaT + 1/2*d2x*deltaT^2 and similarly for delta_y.
        int xDisplacement = (int) ((this.dx * deltaT) + (0.5 * this.d2x * deltaT * deltaT));
        int yDisplacement = (int) ((this.dy * deltaT) + (0.5 * this.d2y * deltaT * deltaT));

        // Apply the displacement calculated to each subsection of the entity's occupied space.
        for(Point subsection : this.occupiedSpace){
            subsection.offset(xDisplacement, yDisplacement);
        }
    }

    /**
     * Update the entity's orientation and transform the occupied space of the entity based on the change in orientation.
     * @param deltaTheta The change in orientation. This absolute value of this parameter should not exceed 360 i.e -360 <= deltaTheta <=360.
     *                      <br>If the deltaTheta falls outside of this range, the value (deltaTheta % 360 wil be used).
     */
    private void rotate(int deltaTheta) {
        deltaTheta = deltaTheta % 360;
        this.orientation += deltaTheta;
        //TODO: Translate occupied space based on change in orientation.
    }
// Getters and Setters -----------------------------------------------------------------------------

    /**
     * Get the list of grid spaces currently occupied by the entity.
     * @return A list of points occupied by the entity.
     */
    public List<Point> getOccupiedSpace() {
        return this.occupiedSpace;
    }

    /**
     * Get the time remaining until this entity expires.
     * @return The remaining lifespan of this entity.
     */
    public int getLifespan() {
        return this.lifespan;
    }
}
