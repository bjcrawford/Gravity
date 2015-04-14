package edu.temple.cis3238.gravity.gravity.controller;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

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

    private GamePlaySurface gamePlaySurface;
    private Model model;
    private boolean run = false;

    public ControllerThread(GamePlaySurface gamePlaySurface, Model model) {
        this.gamePlaySurface = gamePlaySurface;
        this.model = model;
        this.gamePlaySurface.setControllerThread(this);

    }

    public void setRun(boolean run) {
        this.run = run;
    }

    // Just testing a single call to the draw method now, but ultimately this will be where the
    // main game logic loop is.
    @Override
    public void run() {

        // Initialize all necessary components

        // Record start time

        long currentTime = System.currentTimeMillis();
        long deltaTime = 0;
        SurfaceHolder holder = gamePlaySurface.getHolder();
        long testTime = currentTime;
        long testDelta = 0;
        long testFPS = 0;

        //game loop
        while (run) {

            // Pull all events from queue, handle appropriately

            //get the delta time and fix the current time
            deltaTime =  System.currentTimeMillis() - currentTime;
            currentTime = deltaTime + currentTime;

            // Update model, update run
            model.update((float) deltaTime);

            //get the canvas
            Canvas canvas = holder.lockCanvas();

            // Update view
            if(canvas != null){
                synchronized (holder) {
                    //TEST
                    try {
                        gamePlaySurface.drawScene(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //END TEST
                }
                // finished drawing
                holder.unlockCanvasAndPost(canvas);
            }
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
            // Handling of frame rate


        //}

        // Empty contents of event queue checking for loop ending states

        // Call to level fragment listener reporting loop ending state
            //TEST FPS
            testDelta = currentTime - testTime;
            //increase the fps
            testFPS++;
            if(testDelta > 1000){
                Log.d("Game FPS is: ", String.valueOf(testFPS));
                testFPS = 0;
                testTime = System.currentTimeMillis();
            }
            //END TEST

            // Empty contents of event queue checking for loop ending states

            // Call to level fragment listener reporting loop ending state

        }
    }
}
