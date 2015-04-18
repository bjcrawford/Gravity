package edu.temple.cis3238.gravity.gravity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.activity.PlayGameActivity;
import edu.temple.cis3238.gravity.gravity.model.ImageResourceWrapper;
import edu.temple.cis3238.gravity.gravity.model.Model;
import edu.temple.cis3238.gravity.gravity.controller.ControllerThread;

import static edu.temple.cis3238.gravity.gravity.activity.PlayGameActivity.PIXELS_PER_PHYSICS_GRID;


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
    private int levelWidth;
    private int levelHeight;
    private RectF miniMapBounds;
    private Rect fromBackground = new Rect();
    private Rect toBackground = new Rect();
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

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

        this.miniMapBounds = new RectF();
        this.model = model;
        this.imgMap = new HashMap<String, Bitmap>();
        this.levelWidth = model.getUniverseDimensions().x;
        this.levelHeight = model.getUniverseDimensions().y;

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
        canvas.drawColor(Color.RED);
        ImageResourceWrapper player = null;
        fromBackground = new Rect();
        List<ImageResourceWrapper> imgMap = model.getMap();

        //find the player
        for(ImageResourceWrapper img: imgMap){
            if(img.imgResID.substring(0,4).equals("play") ){
                player = new ImageResourceWrapper(img.position, img.imgResID);
                break;
            }
        }
        //set part of background
        float top = ((player.position.x * PIXELS_PER_PHYSICS_GRID  - (sf * levelWidth) )/ (levelWidth * PIXELS_PER_PHYSICS_GRID) * (float)background.getWidth()),
            left = ((player.position.y * PIXELS_PER_PHYSICS_GRID  - (sf * levelWidth) )/ (levelHeight * PIXELS_PER_PHYSICS_GRID) * (float)background.getHeight()),
            bottom = ((player.position.x * PIXELS_PER_PHYSICS_GRID   + (sf * levelWidth) ) / (levelWidth * PIXELS_PER_PHYSICS_GRID) * (float)background.getWidth()),
            right = ((player.position.y * PIXELS_PER_PHYSICS_GRID  + (sf * levelWidth) ) / (levelHeight * PIXELS_PER_PHYSICS_GRID) * (float)background.getHeight());

        Log.d("Dimentions: ", " top " + top + " left "+ left + " buttom "+bottom+" right "+ right);

        fromBackground.set(
                (int)top,
                (int)left,
                (int)bottom,
                (int)right
        );

        //set the frame rect
        toBackground.set(
                0,
                0,
                this.getWidth(),
                this.getHeight()
        );
        canvas.drawBitmap(background, fromBackground, toBackground, null);
    }

    /**
     * draw the entities to the screen
     * @param canvas
     */
    private void drawEntities(Canvas canvas){

        List<ImageResourceWrapper> imgList = model.getFrame((int) standardScreenWidth,(int) standardScreenHeight);

        // a bitmap reference holder
        Bitmap bitmap;

        RectF bitmapBounds = new RectF();

        //set scalar
        final int scalar = PIXELS_PER_PHYSICS_GRID;
        // a rectangle reference holder
        // for each element on the frame
        for(ImageResourceWrapper img: imgList){
            //Log.d(TAG, "IRW: " + img);
            // get the picture of the space object
            //bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img.imgResID, "drawable", context.getPackageName()));
            bitmap = imgMap.get(img.imgResID);
            // set the rect
            bitmapBounds.set(
                    sf * (img.position.x * scalar - bitmap.getWidth() / 2) + this.getWidth() / 2,
                    sf * (img.position.y * scalar - bitmap.getHeight() / 2) + this.getHeight() / 2,
                    sf * (img.position.x * scalar + bitmap.getWidth() / 2) + this.getWidth() / 2,
                    sf * (img.position.y * scalar + bitmap.getHeight() / 2) + this.getHeight() / 2
            );
            // get an angle
            //canvas.rotate(x, miniMapBounds.centerX(), miniMapBounds.centerY());
            // draw the picture of the space object
            canvas.drawBitmap(bitmap, null, bitmapBounds, null);
            //canvas.restore();
        }
    }

    /**
     * draw the small map on the screen
     * @param canvas
     */
    private void drawMap(Canvas canvas){

        List<ImageResourceWrapper> imgList = model.getMap();
        Paint paint = new Paint();

        //fix the rectangle coordinates
        miniMapBounds.set(
                this.getWidth() - sf * levelWidth,
                this.getHeight() - sf * levelHeight,
                this.getWidth(),
                this.getHeight()
        );

        //set the paint property
        //background
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //draw rectangle
        canvas.drawRect(miniMapBounds, paint);

        //draw map objects
        for(ImageResourceWrapper img : imgList){

            //set style
            paint.setStyle(Paint.Style.FILL);

            //set the color
            switch (img.imgResID.substring(0,4)){
                case "play":
                    paint.setColor(0xFF43E119);
                    break;
                case "plan":
                    paint.setColor(0xFF2C46E1);
                    break;
                case "obje":
                    paint.setColor(0xFFF08D33);
                    break;
                default:
                    paint.setColor(Color.RED);
            }
            //finally draw
            //set the object location
            //TODO modify to accept a map instead of a frame
            canvas.drawCircle(
                    this.getWidth() - sf * levelWidth + sf * img.position.x,
                    this.getHeight() - sf * levelHeight + sf * img.position.y,
                    3*sf,
                    paint
            );

            //foreground
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            //draw rectangle
            canvas.drawRect(miniMapBounds, paint);
        }
    }

    /**
     * draw score bord, life and other things
     * @param canvas
     */
    private void drawScore(Canvas canvas){}

}

