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
     * @return The orientation of the entity
     */
    protected int calcOrientation() {
        // Rotation
        // Acceleration vector <d2x, d2y>
        // Theta = aTan(d2y / d2x) ***Ensuring theta is in degrees, not radians***
        // Positions = [0, 1, ...n]
        // Corresponding to [0, 1, ...n] * (360 / n)
        // Position = round(arcTan(Theta / (360 / n)))
        double theta;
        if(this.d2x == 0) {
            theta = this.d2y > 0 ? 90 : 270; // If d2x is 0, theta is a function of d2y exclusively
        }else if(this.d2y == 0) {
            theta = this.d2x > 0 ? 0 : 180; // If d2y is 0, theta is a function of d2x exclusively
        }else {
            theta = Math.toDegrees(Math.atan(Math.abs(this.d2y / this.d2x))); // Otherwise theta must be calculated
            // Figure out which quadrant theta should point to
            if(this.d2x > 0 && this.d2y < 0) {  // Q4
                theta = 360 - theta;
            }else if(this.d2x < 0 && this.d2y < 0) {    // Q3
                theta += 180;
            }else if(this.d2x < 0 && this.d2y > 0) {    // Q2
                theta = 180 - theta;
            }   // Q4 requires no action i.e theta += 0
        }

        float sliceSize = (float) (360 / this.shapes.size());
        return ((int) (((float) theta) / sliceSize)) % this.shapes.size(); // Ensure that the integer returned is in Zn
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
     */
    public JSONObject toJSON() {

        JSONObject selfAsJSON = super.toJSON();
        try {
            selfAsJSON.put("dx0", this.dx);
            selfAsJSON.put("dy0", this.dy);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return selfAsJSON;
    }

// Getters and Setters -----------------------------------------------------------------------------

}