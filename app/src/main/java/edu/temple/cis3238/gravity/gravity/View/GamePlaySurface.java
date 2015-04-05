package edu.temple.cis3238.gravity.gravity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

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
     * set the reference to the entity list
     * @param entityList a list of entities
     */
    public void setEntityList(ArrayList<Entity> entityList){
        this.entityList = entityList;
    }

    /**
     * draws all graphics entities on the screen
     * @throws Exception
     */

    /*
    public void drawScene()throws Exception{

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

}

