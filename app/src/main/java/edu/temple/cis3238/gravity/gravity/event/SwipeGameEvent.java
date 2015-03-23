package edu.temple.cis3238.gravity.gravity.event;

/**
 * A GameEvent subclass for gesture based touch input.
 *
 * @author: Brett Crawford
 * @version 1.0a last modified 3/21/2015
 */
public class SwipeGameEvent extends GameEvent {

    /* The x component of the swipe */
    private final float sx;

    /* The y component of the swipe */
    private final float sy;

    public SwipeGameEvent(float sx, float sy) {

        this.sx = sx;
        this.sy = sy;
    }

    public float getSy() {
        return sy;
    }

    public float getSx() {
        return sx;
    }
}
