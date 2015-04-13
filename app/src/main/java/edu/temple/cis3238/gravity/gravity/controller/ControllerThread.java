package edu.temple.cis3238.gravity.gravity.controller;

import android.graphics.Canvas;

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

        //while(run){

            // Pull all events from queue, handle appropriately

            // Update model, update run

            // Update view
            Canvas canvas = gamePlaySurface.getHolder().lockCanvas();
            if(canvas != null){
                synchronized (gamePlaySurface.getHolder()) {
                    gamePlaySurface.drawSomething(canvas);
                }
                gamePlaySurface.getHolder().unlockCanvasAndPost(canvas);
            }

            // Handling of frame rate


        //}

        // Empty contents of event queue checking for loop ending states

        // Call to level fragment listener reporting loop ending state
    }
}
