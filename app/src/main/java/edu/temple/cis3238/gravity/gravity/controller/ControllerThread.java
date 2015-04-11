package edu.temple.cis3238.gravity.gravity.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.View.GamePlaySurface;
import edu.temple.cis3238.gravity.gravity.model.Model;

/**
 * A controller thread.
 *
 * @author Ayad Aliomer
 * @author Brett Crawford
 * @version 1.0b last modified 4/6/2015
 */
public class ControllerThread extends Thread {
    private  Model model;
    private GamePlaySurface gamePlaySurface;
    private boolean running = false;

    public ControllerThread(GamePlaySurface gamePlaySurface, Model model) {
        this.gamePlaySurface = gamePlaySurface;
        this.gamePlaySurface.setControllerThread(this);

    }

    public void setRunning(boolean run) {
        running = run;
    }

    // Just testing a single call to the draw method now, but ultimately this will be where the
    // main game logic loop is.
    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long deltaTime = 0;
        SurfaceHolder holder = gamePlaySurface.getHolder();
        long testTime = currentTime;
        long testDelta = 0;
        long testFPS = 0;

        //initiate the model

        //game loop
        while(running){

            // Pull all events from queue, handle appropriately

            //get the delta time and fix the current time
            deltaTime =  System.currentTimeMillis() - currentTime;
            currentTime = deltaTime + currentTime;

            // Update model, update run
            model.update((float) deltaTime);

            //get the canvas
            Canvas canvas = holder.lockCanvas();

            //draw on the canvas
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
            if(deltaTime < 32){
                try {
                    sleep(32 - deltaTime);
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
                Log.d("Game FPS is: ", String.valueOf(testFPS));
                testFPS = 0;
                testTime = System.currentTimeMillis();
                testDelta = 0;
            }
            //END TEST

            // Empty contents of event queue checking for loop ending states

            // Call to level fragment listener reporting loop ending state

        }
    }
}
