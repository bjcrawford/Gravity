package edu.temple.cis3238.gravity.gravity.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.ImageResourceWrapper;
import edu.temple.cis3238.gravity.gravity.model.Model;
import edu.temple.cis3238.gravity.gravity.model.Point;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.entity.Entity;

/**
 * Created by Ayad on 3/28/2015.
 * version 1
 * last modified 3/29/2015
 */
public class GamePlaySurface extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder; //surface holder
    private ArrayList<Entity> entityList; // list of graphic entities
    private Context context;
    private Canvas canvas;
    private Bitmap bitmap;
    private ImageResourceWrapper imgRec;
    private Model gameModel;
    private float sf;

    public GamePlaySurface(Context context){
        super(context);
        getHolder().addCallback(this);
        this.context = context;
    }

    GamePlaySurface(Context context, AttributeSet attrs){
        super(context, attrs);
        getHolder().addCallback(this);
        this.context = context;
    }

    /**
     * draw one frame of the scene
     * @param gameModel
     * @param sf
     */
    //TODO make sure that the init function has everything
    public void init(Model gameModel, float sf){
        this.gameModel = gameModel;
        this.sf = sf;
    }

    /**
     * draws all graphics entities on the screen
     * @throws Exception
     * @param x: just for testing
     * @param  y: just for testing
     */
    public void drawScene(Canvas canvas, int x , int y)throws Exception{
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
        List<ImageResourceWrapper> imgList = gameModel.getFrame(this.getWidth(),this.getHeight());

        //a bitmap reference holder
        Bitmap bitmap;
        //a rectangle reference holder
        RectF rectF = new RectF();
        //for each element on the frame
        for(ImageResourceWrapper img: imgList){
            //get the picture of the space object
            bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img.imgResID, "drawable", context.getPackageName()));
            //set the rect
            rectF.set(sf * (img.position.x - bitmap.getWidth()/2),
                    sf * (img.position.y - bitmap.getHeight()/2),
                    sf * (img.position.x + bitmap.getWidth()/2),
                    sf * (img.position.y + bitmap.getHeight()/2));
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


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        this.holder = holder;
        try {
            canvas = holder.lockCanvas(null);

            synchronized (holder) {
                draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    //FOR TESTING ONLY
    public Context getCurrentContext(){return context;}

}

