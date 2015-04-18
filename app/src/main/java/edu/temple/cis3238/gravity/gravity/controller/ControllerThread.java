package edu.temple.cis3238.gravity.gravity.controller;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.event.GameEvent;
import edu.temple.cis3238.gravity.gravity.event.GameEventQueue;
import edu.temple.cis3238.gravity.gravity.event.SwipeGameEvent;
import edu.temple.cis3238.gravity.gravity.model.Model;
import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;
import edu.temple.cis3238.gravity.gravity.view.GamePlaySurface;

/**
 * A controller thread.
 *
 * @author Ayad Aliomer
 * @author Brett Crawford
 * @version 1.0c last modified 4/17/2015
 */
public class ControllerThread extends Thread {

    /* Debug tag */
    private static final String TAG = "ControllerThread";

    /* An interface for communicating with the level fragment */
    public interface OnControllerThreadInteractionListener {
        public void OnControllerThreadEnd(GameState gamestate);
    }

    /* The interface to communicate with the level fragment */
    private OnControllerThreadInteractionListener levelFragmentListener;

    /* The SurfaceView the game will be drawn to */
    private GamePlaySurface gamePlaySurface;

    /* The game model for the current level */
    private Model model;

    /* The thread safe event queue */
    private GameEventQueue eventQueue;

    /* The run state of the game */
    private boolean run;

    /* The pause state of the game */
    private boolean pause;

    /* An list of pending swipe events */
    private List<SwipeGameEvent> pendingSwipeEvents;

    /**
     * Constructs a controller thread. Both run and pause states are set to false.
     * The game play surface will the call run method of this class during the
     * onSurfaceCreated event.
     *
     * @param levelFragmentListener The level fragment listener interface
     * @param gamePlaySurface The game play surface view component
     * @param model The game model component
     * @param eventQueue The game event queue
     */
    public ControllerThread(OnControllerThreadInteractionListener levelFragmentListener,
                            GamePlaySurface gamePlaySurface, Model model, GameEventQueue eventQueue) {
        this.levelFragmentListener = levelFragmentListener;
        this.gamePlaySurface = gamePlaySurface;
        this.gamePlaySurface.setControllerThread(this);
        this.model = model;
        this.eventQueue = eventQueue;
        this.run = false;
        this.pause = false;
        this.pendingSwipeEvents = new ArrayList<SwipeGameEvent>();
    }

    @Override
    public void run() {

        // Initialize all necessary components

        // Record start time

        long currentTime = System.currentTimeMillis();
        long pauseStartTime = 0;
        long deltaTime = 0;
        long testTime = currentTime;
        long testDelta = 0;
        long testFPS = 0;
        boolean wasPaused = false;

        //game loop
        while (run) {

            if (pause) {
                // Record pause start time to fix delta after pause is released
                pauseStartTime = System.currentTimeMillis();
            }

            // Handle pause state
            while (pause) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    wasPaused = true;
                }
            }

            if (wasPaused) {
                // Add length of pause to the current time
                currentTime += (System.currentTimeMillis() - pauseStartTime);
                wasPaused = false;
            }

            //get the delta time and fix the current time
            deltaTime =  System.currentTimeMillis() - currentTime;
            currentTime = deltaTime + currentTime;

            // Pull all events from queue, handle appropriately
            GameEvent gameEvent;
            while ((gameEvent = eventQueue.poll()) != null) {

                if (gameEvent instanceof SwipeGameEvent) {
                    pendingSwipeEvents.add((SwipeGameEvent) gameEvent);
                }

            }

            // Apply and consume swipe events
            for (SwipeGameEvent swe : pendingSwipeEvents) {
                swe.updateDt(deltaTime);
                model.receiveInput(swe.getSx(), swe.getSy());
                if (swe.getDt() < 0) {
                    pendingSwipeEvents.remove(swe);
                }
            }

            // Update model, update run
            model.update((float) deltaTime);
            if (!model.getGameStateModel().getPlayable()) {
                run = false;
            }

            //get the canvas
            Canvas canvas = gamePlaySurface.getHolder().lockCanvas();

            // Update view
            if(canvas != null){
                synchronized (gamePlaySurface.getHolder()) {
                    try {
                        gamePlaySurface.drawScene(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                gamePlaySurface.getHolder().unlockCanvasAndPost(canvas);
            }

            // Handling of frame rate
            //check if need to sleep given the
            //this gives 30fps
//            if(deltaTime < 64){
//                try {
//                    sleep(64 - deltaTime);
//                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


            //TEST FPS
            testDelta = currentTime - testTime;
            //increase the fps
            testFPS++;
            if(testDelta > 1000){
                Log.d("Game FPS is: ", String.valueOf(testFPS));
                testFPS = 0;
                testTime = System.currentTimeMillis();
            }

        }

        // Empty contents of event queue checking for loop ending states

        // Call to level fragment levelFragmentListener reporting loop ending state
        onControllerThreadEnd(model.getGameStateModel());


    }

    /**
     *  Handles passing the game state to the level fragment when the controller
     *  thread has ended.
     *
     * @param gamestate The end level gamestate
     */
    public void onControllerThreadEnd(GameState gamestate) {
        if (levelFragmentListener != null) {
            levelFragmentListener.OnControllerThreadEnd(gamestate);
        }
    }

    /**
     * Sets the runnable state of the game
     *
     * @param run False to end the game, otherwise continue running
     */
    public synchronized void setRun(boolean run) {
        this.run = run;
    }

    /**
     * Sets the paused state of the game
     *
     * @param pause True to pause the game, otherwise continue running
     */
    public synchronized void setPause(boolean pause) {
        this.pause = pause;
    }


}
