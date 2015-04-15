package edu.temple.cis3238.gravity.gravity.controller;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.concurrent.ConcurrentLinkedQueue;

import edu.temple.cis3238.gravity.gravity.event.GameEvent;
import edu.temple.cis3238.gravity.gravity.event.SwipeGameEvent;
import edu.temple.cis3238.gravity.gravity.model.Model;
import edu.temple.cis3238.gravity.gravity.view.GamePlaySurface;

/**
 * A controller thread.
 *
 * @author Ayad Aliomer
 * @author Brett Crawford
 * @version 1.0b last modified 4/6/2015
 */
public class ControllerThread extends Thread {

    private static final String TAG = "ControllerThread";

    private GamePlaySurface gamePlaySurface;
    private Model model;
    private ConcurrentLinkedQueue<GameEvent> eventQueue;
    private boolean run;
    private boolean pause;

    public ControllerThread(GamePlaySurface gamePlaySurface, Model model, ConcurrentLinkedQueue<GameEvent> eventQueue) {
        this.gamePlaySurface = gamePlaySurface;
        this.gamePlaySurface.setControllerThread(this);
        this.model = model;
        this.eventQueue = eventQueue;
        this.run = false;
        this.pause = false;
    }

    public synchronized void setRun(boolean run) {
        this.run = run;
    }

    public synchronized void setPause(boolean pause) {
        this.pause = pause;
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
                    model.receiveInput((SwipeGameEvent) gameEvent);
                 //   Log.d(TAG, "Sending swipe event");
                }

            }


            // Update model, update run
            model.update((float) deltaTime);

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
            if(deltaTime < 64){
                try {
                    sleep(64 - deltaTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //TEST FPS
            testDelta = currentTime - testTime;
            //increase the fps
            testFPS++;
            if(testDelta > 1000){
             //   Log.d("Game FPS is: ", String.valueOf(testFPS));
                testFPS = 0;
                testTime = System.currentTimeMillis();
            }

        }

        // Empty contents of event queue checking for loop ending states

        // Call to level fragment listener reporting loop ending state

    }
}
