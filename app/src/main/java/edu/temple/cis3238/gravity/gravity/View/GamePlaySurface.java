package edu.temple.cis3238.gravity.gravity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.activity.PlayGameActivity;
import edu.temple.cis3238.gravity.gravity.model.ImageResourceWrapper;
import edu.temple.cis3238.gravity.gravity.model.Model;
import edu.temple.cis3238.gravity.gravity.controller.ControllerThread;


/**
 * A custom game play surface view.
 *
 * @author Ayad Aliomer
 * @author Brett Crawford
 * @version 1.0b last modified 4/6/2015
 */
public class GamePlaySurface extends SurfaceView {

    private static final String TAG = "GamePlaySurface";

    private ControllerThread controllerThread;
    private Model model;
    private Context context;
    private HashMap<String, Bitmap> imgMap;
    private float sf;
    private float standardScreenWidth;
    private float standardScreenHeight;
    private ArrayList<Bitmap> bitmaps;

    public GamePlaySurface(Context context){
        super(context);
        this.context = context;
    }

    public GamePlaySurface(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
    }

    public GamePlaySurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    // We will need a reference to the controller thread so that when the surfaceview is ready
    // it can launch the thread.
    public void setControllerThread(ControllerThread controllerThread) {
        this.controllerThread = controllerThread;
    }

    // This initializes the surface view by grabbing a reference to the surface holder and
    // defining the call back methods. This is a more appropriate place for the methods.
    public void init(Model model) {
        this.model = model;
        imgMap = new HashMap<String, Bitmap>();

        //cache all images
        Bitmap bitmap;
        for(int i = 0; i < 12; i++){
            // get planets
            bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("planet"+String.valueOf(i), "drawable", context.getPackageName()));
            imgMap.put("planet"+String.valueOf(i),bitmap);
            // get players
            bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("player"+String.valueOf(i), "drawable", context.getPackageName()));
            imgMap.put("player"+String.valueOf(i),bitmap);
            // get objectives
            bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("objective"+String.valueOf(i), "drawable", context.getPackageName()));
            imgMap.put("objective"+String.valueOf(i),bitmap);
        }

        //add call back
        getHolder().addCallback(new SurfaceHolder.Callback() {

            // When the surface is ready, start the thread
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                GamePlaySurface.this.sf = GamePlaySurface.this.getWidth() / PlayGameActivity.STANDARD_WIDTH;
                GamePlaySurface.this.standardScreenWidth = GamePlaySurface.this.getWidth() / sf;
                GamePlaySurface.this.standardScreenHeight = GamePlaySurface.this.getHeight() / sf;
                Log.d(TAG, "screen dimens: " + GamePlaySurface.this.standardScreenWidth + " x " + GamePlaySurface.this.standardScreenHeight);
                Log.d(TAG, "sf: " + String.valueOf(sf));
                controllerThread.setRun(true);
                controllerThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            // When the surface is being destroyed, attempt to alert the controller thread
            // so we can shut down cleanly.
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                controllerThread.setRun(false);
                while (retry) {
                    try {
                        controllerThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    /**
     * draws all graphics entities on the screen
     * @throws Exception
     */
    public void drawScene(Canvas canvas) throws Exception {

        //make sure the surface is ready
        if(canvas == null) throw new Exception("Canvas is null: the canvas have not been constructed on the surfaceView");
        //first, draw the background
        drawBackground(canvas);
        //draw the separate layers on the screen
        drawEntities(canvas);
        drawMap(canvas);
        drawScore(canvas);
    }

    /**
     * draw the background of the scene
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    /**
     * draw the entities to the screen
     * @param canvas
     */
    private void drawEntities(Canvas canvas){

        List<ImageResourceWrapper> imgList = model.getFrame((int) standardScreenWidth,(int) standardScreenHeight);

        // a bitmap reference holder
        Bitmap bitmap;
        // a rectangle reference holder
        RectF rectF = new RectF();
        // for each element on the frame
        //Log.d(TAG, "Entities in frame: " + imgList.size());
        for(ImageResourceWrapper img: imgList){
            //Log.d(TAG, "IRW: " + img);
            // get the picture of the space object
            //bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img.imgResID, "drawable", context.getPackageName()));
            bitmap = imgMap.get(img.imgResID);
            // set the rect
            rectF.set(
                    sf * (img.position.x - bitmap.getWidth()/2) + this.getWidth()/2,
                    sf * (img.position.y - bitmap.getHeight()/2) + this.getHeight()/2,
                    sf * (img.position.x + bitmap.getWidth()/2) + this.getWidth()/2,
                    sf * (img.position.y + bitmap.getHeight()/2) + this.getHeight()/2
            );
            // get an angle
            //canvas.rotate(x, rectF.centerX(), rectF.centerY());
            // draw the picture of the space object
            canvas.drawBitmap(bitmap, null, rectF, null);
            //canvas.restore();
        }
    }

    /**
     * draw the small map on the screen
     * @param canvas
     */
    private void drawMap(Canvas canvas){}

    /**
     * draw score bord, life and other things
     * @param canvas
     */
    private void drawScore(Canvas canvas){}

}

