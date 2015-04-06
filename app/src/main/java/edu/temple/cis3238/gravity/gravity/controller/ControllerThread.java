package edu.temple.cis3238.gravity.gravity.controller;

import android.graphics.Canvas;

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
        //while(running){

            Canvas canvas = gamePlaySurface.getHolder().lockCanvas();

            if(canvas != null){
                synchronized (gamePlaySurface.getHolder()) {
                    gamePlaySurface.drawSomething(canvas);
                }
                gamePlaySurface.getHolder().unlockCanvasAndPost(canvas);
            }

            //try {
            //    sleep(30);
            //}
            //catch (InterruptedException e) {
            //    e.printStackTrace();
            //}

        //}
    }
}
