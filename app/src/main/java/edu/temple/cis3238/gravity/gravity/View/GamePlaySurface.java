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

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.ImageResourceWrapper;
import edu.temple.cis3238.gravity.gravity.model.Model;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.entity.Entity;
import edu.temple.cis3238.gravity.gravity.controller.ControllerThread;


/**
 * A custom game play surface view.
 *
 * @author Ayad Aliomer
 * @author Brett Crawford
 * @version 1.0b last modified 4/6/2015
 */
public class GamePlaySurface extends SurfaceView {

    private SurfaceHolder holder; //surface holder
    private ArrayList<Entity> entityList; // list of graphic entities
    private Context context;
    private Canvas canvas;
    private Bitmap bitmap;
    private ImageResourceWrapper imgRec;
    private Model model;
    private float sf;
    private static final String TAG = "GamePlaySurface";
    private ArrayList<Bitmap> bitmaps;
    private HashMap<String, Bitmap> imgMap;
    private SurfaceHolder surfaceHolder;

    // This will be the controller class
    private ControllerThread controllerThread;

    // Unneccesary, context is handled by the super class, use getContext() if needed
    //private Context context;


    // Ayad, I think the reason the instantiation via the xml layout was not working for you
    // was because you need all three of these constructors and they need to be public.
    // There was only two before and only the first was labeled as public.
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

    // This initializes the surface view by grabbing a reference to the surface holder and
    // defining the call back methods. This is a more appropriate place for the methods.
    public void init(Model model) {
        this.model = model;
        surfaceHolder = getHolder();
        imgMap = new HashMap<String, Bitmap>();

        //cache all images
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
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            // When the surface is ready, start the thread
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                GamePlaySurface.this.sf = GamePlaySurface.this.getWidth()/1400f;
                Log.d("screen width: ",String.valueOf(GamePlaySurface.this.getWidth()));
                Log.d("sf is: ",String.valueOf(sf));
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
                //boolean retry = true;
                //controllerThread.setRunning(false);
                //while (retry) {
                //    try {
                //        controllerThread.join();
                //        retry = false;
                //    }
                //    catch (InterruptedException e) {
                //    }
                //}
            }});
    }

    // We will need a reference to the controller thread so that when the surfaceview is ready
    // it can launch the thread.
    public void setControllerThread(ControllerThread controllerThread) {
        this.controllerThread = controllerThread;
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


    //Helper methods to drawScene()

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

        //TEST CODE
        List<ImageResourceWrapper> imgList = model.getFrame(this.getWidth(),this.getHeight());

        //a bitmap reference holder
        Bitmap bitmap;
        //a rectangle reference holder
        RectF rectF = new RectF();
        //for each element on the frame
        for(ImageResourceWrapper img: imgList){
            //get the picture of the space object
//            bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img.imgResID, "drawable", context.getPackageName()));
            bitmap = imgMap.get(img.imgResID);
            //set the rect
            rectF.set(
                    sf * (img.position.x - bitmap.getWidth()/2) + this.getWidth()/2,
                    sf * (img.position.y - bitmap.getHeight()/2) + this.getHeight()/2,
                    sf * (img.position.x + bitmap.getWidth()/2) + this.getWidth()/2,
                    sf * (img.position.y + bitmap.getHeight()/2) + this.getHeight()/2
            );
            //get an angle
          //  canvas.rotate(x, rectF.centerX(), rectF.centerY());
            //draw the picture of the space object
            canvas.drawBitmap(bitmap, null, rectF, null);
          //  canvas.restore();

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



    // Just a testing draw method. The real workhorse of this class will be your drawScene method.
    // Also in any draw method, the canvas is passed in by the controller thread.
    public void drawSomething(Canvas canvas) {


//        // Just testing the draw method, this method should eventually take in a list of ImageResourceWrapper
//      //  Log.d(TAG, "Canvas width: " + canvas.getWidth());
//      //  Log.d(TAG, "Canvas height: " + canvas.getHeight());
//
//        // Determine scaling factor
//        float sf = canvas.getWidth() / 1400f;
//      //  Log.d(TAG, "Scaling Factor: " + sf);
//
//        // Draw player 0
//        Bitmap player0 = BitmapFactory.decodeResource(getResources(), R.drawable.player0);
//        int player0x = 0;
//        int player0y = 0;
//        int player0Width = player0.getWidth();
//        int player0Height = player0.getHeight();
//    //    Log.d(TAG, "Player0 width: " + player0Width);
//    //    Log.d(TAG, "Player0 height: " + player0Height);
//        RectF player0Dimen = new RectF(
//                sf * player0x,
//                sf * player0y,
//                sf * player0x + sf * player0Width,
//                sf * player0y + sf * player0Height);
//
//        canvas.drawBitmap(player0, null, player0Dimen, null);
//
//        // Draw player 3
//        Bitmap player3 = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
//        int player3x = 200;
//        int player3y = 200;
//        int player3Width = player3.getWidth();
//        int player3Height = player3.getHeight();
//    //    Log.d(TAG, "Player3 width: " + player3Width);
//     //   Log.d(TAG, "Player3 height: " + player3Height);
//        RectF player3Dimen = new RectF(
//                sf * player3x,
//                sf * player3y,
//                sf * player3x + sf * player3Width,
//                sf * player3y + sf * player3Height);
//
//        canvas.drawBitmap(player3, null, player3Dimen, null);
//
//        // Draw grid
//        Paint whitePaint = new Paint();
//        whitePaint.setColor(0xFFFFFFFF);
//        for (int x = 0; x < canvas.getWidth(); x += sf * 50) {
//            for (int y = 0; y < canvas.getHeight(); y += sf * 50) {
//                canvas.drawPoint(x, y, whitePaint);
//            }
//        }

        //TESTING
        int rate = 10;
        canvas.drawColor(Color.BLACK);
        if(bitmaps == null){
            bitmaps = new ArrayList<Bitmap>();
            //add bitmaps
            for(int i = 0; i < rate; i ++){
                bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.planet1));
            }
            //draw bitmaps
            for(Bitmap bitmap: bitmaps){
                double rand = Math.random();
                canvas.drawBitmap(bitmap, (float) rand * this.getWidth(),(float) Math.random() * this.getHeight()/2, null);
            }

        }else{
            for(Bitmap bitmap: bitmaps){
                double rand = Math.random();
                canvas.drawBitmap(bitmap, (float) rand * this.getWidth(),(float) rand * this.getHeight()/2, null);
            }
        }
        //END TEST


    }
    //FOR TESTING ONLY
    public Context getCurrentContext(){return context;}

}

