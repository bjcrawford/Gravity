package edu.temple.cis3238.gravity.gravity.event;

/**
 * A GameEvent subclass for gesture based touch input.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/21/2015
 */
public class SwipeGameEvent extends GameEvent {

    /* The x component of the swipe */
    private final float sx;

    /* The y component of the swipe */
    private final float sy;

    /**
     * The default constructor for this class. Sets the x and y components of
     * the velocity of the swipe event.
     * @param sx The x component.
     * @param sy The y component.
     */
    public SwipeGameEvent(float sx, float sy) {
        this.sx = sx;
        this.sy = sy;
    }

    /**
     * Returns the x component of the swipe event.
     * @return The x component.
     */
    public float getSy() {
        return sy;
    }

    /**
     * Returns the y component of the swipe event.
     * @return the y component.
     */
    public float getSx() {
        return sx;
    }
}
