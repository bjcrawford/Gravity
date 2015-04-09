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

/**
 * A controller thread.
 *
 * @author Ayad Aliomer
 * @author Brett Crawford
 * @version 1.0b last modified 4/6/2015
 */
public class ControllerThread extends Thread {

    private GamePlaySurface gamePlaySurface;
    private boolean running = false;

    public ControllerThread(GamePlaySurface gamePlaySurface) {
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

        //TEST
        int rate = 50;
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        //add bitmaps
        for(int i = 0; i < rate; i ++){
            bitmaps.add(BitmapFactory.decodeResource(gamePlaySurface.getResources(), R.drawable.planet1));
        }
        //END TESTg

        //game loop
        while(running){

            //get the delta time and fix the current time
            deltaTime =  System.currentTimeMillis() - currentTime;
            currentTime = deltaTime + currentTime;

            //get the canvas
            Canvas canvas = holder.lockCanvas();

            //draw on the canvas
            if(canvas != null){
                synchronized (holder) {
                    //TEST
                    gamePlaySurface.drawSomething(canvas, bitmaps);
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
                testTime = currentTime;
                testDelta = 0;
            }
            //END TEST


        }
    }
}
