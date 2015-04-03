package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
abstract public class MobileEntity extends Entity {

// Fields ------------------------------------------------------------------------------------------

    /**The entity's current velocity in the x direction.*/
    protected int dx;

    /**The entity's current velocity in the y direction.*/
    protected int dy;

    /**The acceleration in the x direction currently being experienced by this entity.*/
    protected float d2x;

    /**The acceleration in the y direction currently being experienced by this entity.*/
    protected float d2y;


// Constructors ------------------------------------------------------------------------------------

// Private -----------------------------------------------------------------------------------------

// Protected ---------------------------------------------------------------------------------------

    /**
     * Update the velocity of the entity based on its current acceleration, velocity, and the elapsed time given as input.
     * @param deltaT The time that has elapsed since the last call to the entity's accelerate method; used to calculate the entity's new velocity.
     */
    protected void accelerate(float deltaT) {
        // Calculate the change in velocity experienced by the entity during the given interval:
        // delta_v = a * deltaT for the x and y components.
        this.dx += this.d2x * deltaT;
        this.dy += this.d2y * deltaT;
    }

    /**
     * Update the position of the entity based on its current acceleration, velocity, and the elapsed time given as input.
     * @param deltaT The time that has elapsed since the last call to the entity's accelerate method; used to calculate the entity's new velocity.
     */
    protected void displace(float deltaT) {
        // Calculate the displacement experienced by the entity during the given interval:
        // delta_x = dx*deltaT + 1/2*d2x*deltaT^2 and similarly for delta_y.
        int xDisplacement = (int) ((this.dx * deltaT) + (0.5 * this.d2x * deltaT * deltaT));
        int yDisplacement = (int) ((this.dy * deltaT) + (0.5 * this.d2y * deltaT * deltaT));

        // Apply displacement to the entity's position
        this.position.offset(xDisplacement, yDisplacement);
    }

    /**
     * Calculate the orientation of the entity based on its current acceleration vector i.e <d2x, d2y>
     * @return The orientation of the entity [0, 11]
     */
    protected int calcOrientation() {
        // Rotation
        // Acceleration vector <d2x, d2y>
        // Theta = aTan(d2y / d2x) ***Ensuring theta is in degrees, not radians***
        // Positions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        // Corresponding to [0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360]
        // Position = round(Theta / 30)
        double theta;
        if(this.d2x == 0) {
            theta = this.d2y > 0 ? 90 : 270; // If d2x is 0, theta is a function of d2y exclusively
        }else {
            theta = Math.toDegrees(Math.atan(this.d2y / this.d2x)); // Otherwise theta must be calculated
        }
        return Math.round(((float) theta) / 30.0f) % 12; // Ensure that the integer returned is in Z12
    }

// Public ------------------------------------------------------------------------------------------

    /**
     * Modify the values of acceleration being experienced by the entity.
     * @param delta_d2x The value of acceleration in the x direction to be applied to the entity.
     * @param delta_d2y The value of acceleration in the y direction to be applied to the entity.
     */
    abstract public void applyAcceleratingForce(float delta_d2x, float delta_d2y);

    /**
     * Returns a JSON object representation of the entity.
     * @return A JSON object containing the data of the entity.
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException{
        JSONObject selfAsJSON = super.toJSON();
        selfAsJSON.put("dx0", this.dx);
        selfAsJSON.put("dy0", this.dy);
        return selfAsJSON;
    }

// Getters and Setters -----------------------------------------------------------------------------

}