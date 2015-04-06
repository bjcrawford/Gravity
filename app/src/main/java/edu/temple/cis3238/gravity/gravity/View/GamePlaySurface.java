package edu.temple.cis3238.gravity.gravity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import edu.temple.cis3238.gravity.gravity.R;
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
    }

    public GamePlaySurface(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public GamePlaySurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

    /*
    public void drawScene(Canvas canvas)throws Exception{

        //get the holder for the surface
        canvas = holder.lockCanvas();
        //make sure the surface is ready
        if(canvas == null) throw new Exception("Canvas is null: the canvas have not been constructed on the surfaceView");
        //visit each entity and draw it
        for(Entity entity: entityList){
            //get the resource name
            String imgResId = entity.getName();
            //load the correct bitmap
            bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imgResId, "drawable", context.getPackageName()));
            //draw the bitmap
            canvas.drawBitmap(bitmap, entity.getX()-bitmap.getWidth()/2, entity.getY()-bitmap.getHeight()/2, null);
        }
        //at the end
        holder.unlockCanvasAndPost(canvas);
    }
    */

    // This initializes the surface view by grabbing a reference to the surface holder and
    // defining the call back methods. This is a more appropriate place for the methods.
    public void init() {

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            // When the surface is ready, start the thread
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //controllerThread.setRunning(true);
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

    // Just a testing draw method. The real workhorse of this class will be your drawScene method.
    // Also in any draw method, the canvas is passed in by the controller thread.
    public void drawSomething(Canvas canvas) {

        // Just testing the draw method, this method should eventually take in a list of ImageResourceWrapper
        Log.d(TAG, "Canvas width: " + canvas.getWidth());
        Log.d(TAG, "Canvas height: " + canvas.getHeight());

        // Determine scaling factor
        float sf = canvas.getWidth() / 1400f;
        Log.d(TAG, "Scaling Factor: " + sf);

        // Draw player 0
        Bitmap player0 = BitmapFactory.decodeResource(getResources(), R.drawable.player0);
        int player0x = 0;
        int player0y = 0;
        int player0Width = player0.getWidth();
        int player0Height = player0.getHeight();
        Log.d(TAG, "Player0 width: " + player0Width);
        Log.d(TAG, "Player0 height: " + player0Height);
        RectF player0Dimen = new RectF(
                sf * player0x,
                sf * player0y,
                sf * player0x + sf * player0Width,
                sf * player0y + sf * player0Height);

        canvas.drawBitmap(player0, null, player0Dimen, null);

        // Draw player 3
        Bitmap player3 = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
        int player3x = 200;
        int player3y = 200;
        int player3Width = player3.getWidth();
        int player3Height = player3.getHeight();
        Log.d(TAG, "Player3 width: " + player3Width);
        Log.d(TAG, "Player3 height: " + player3Height);
        RectF player3Dimen = new RectF(
                sf * player3x,
                sf * player3y,
                sf * player3x + sf * player3Width,
                sf * player3y + sf * player3Height);

        canvas.drawBitmap(player3, null, player3Dimen, null);

        // Draw grid
        Paint whitePaint = new Paint();
        whitePaint.setColor(0xFFFFFFFF);
        for (int x = 0; x < canvas.getWidth(); x += sf * 50) {
            for (int y = 0; y < canvas.getHeight(); y += sf * 50) {
                canvas.drawPoint(x, y, whitePaint);
            }
        }
    }

}

