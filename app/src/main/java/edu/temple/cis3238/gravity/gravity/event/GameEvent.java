package edu.temple.cis3238.gravity.gravity.event;

/**
 * An abstract class for any external event passed to the controller thread. This can
 * include user input(swipes), system events(pauses), network input, etc.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/21/2015
 */
public abstract class GameEvent {

    /* Records the time of the event */
    private final long eventTime;

    /**
     * The default constructor for this class. Initializes the eventTime
     * property with the current system time (ms).
     */
    public GameEvent() {
        eventTime = System.currentTimeMillis();
    }

    /**
     * Returns the time the event occurred in the systems time (ms).
     * @return The time of the event
     */
    public long getEventTime() {
        return eventTime;
    }
}
