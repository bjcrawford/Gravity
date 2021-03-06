package edu.temple.cis3238.gravity.gravity.event;

/**
 * A GameEvent subclass for gesture based touch input.
 *
 * @author Brett Crawford
 * @version 1.0b last modified 4/17/2015
 */
public class SwipeGameEvent extends GameEvent {

    /* The time delta (ms) over which to apply the swipe */
    private float dt;

    /* The x component of the swipe */
    private final float sx;

    /* The y component of the swipe */
    private final float sy;

    /**
     * The default constructor for this class. Sets the x and y components of
     * the velocity of the swipe event and the time delta.
     *
     * @param dt The time delta (ms).
     * @param sx The x component.
     * @param sy The y component.
     */
    public SwipeGameEvent(float dt, float sx, float sy) {
        this.dt = dt;
        this.sx = sx;
        this.sy = sy;
    }

    /**
     * Returns the time delta of the event.
     *
     * @return The time delta.
     */
    public float getDt() {
        return dt;
    }

    /**
     * Decreases the time delta associated with this swipe event.
     *
     * @param elapsed The time elapsed (ms).
     */
    public void updateDt(float elapsed) {
        dt -= elapsed;
    }

    /**
     * Returns the x component of the swipe event.
     *
     * @return The x component.
     */
    public float getSy() {
        return sy;
    }

    /**
     * Returns the y component of the swipe event.
     *
     * @return the y component.
     */
    public float getSx() {
        return sx;
    }
}
