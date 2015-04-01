package edu.temple.cis3238.gravity.gravity.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import edu.temple.cis3238.gravity.gravity.R;
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
    Context context;
    Bitmap background;

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

