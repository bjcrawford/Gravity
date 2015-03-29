package edu.temple.cis3238.gravity.gravity.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import edu.temple.cis3238.gravity.gravity.model.Point;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.entity.Entity;

/**
 * Created by Ayad on 3/28/2015.
 * version 1
 * last modified 3/29/2015
 */
public class GamePlaySurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder holder; //surface holder
    private ArrayList<Entity> entityList; // list of graphic entities
    //Context context; // current
    Bitmap background;

    public GamePlaySurface(Context context){
        super(context);
        getHolder().addCallback(this);
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


        //TESTING DRAWING ON SCREEN
        Entity e1 = new Entity("planet", new Point(50,50), 1);
        Entity e2 = new Entity("planet", new Point(500,500), 2);
        Entity e3 = new Entity("player", new Point(150,150), 3);
        this.entityList = new ArrayList<Entity>();
        entityList.add(e1);
        entityList.add(e2);
        entityList.add(e3);
        //END TEST


        // TODO draw background
        canvas.drawColor(android.R.color.black);




        // Draw all entities one by one
        for(Entity entity: entityList){
            int id = getResources().getIdentifier(entity.getName()+Integer.toString(entity.getOrientation()), "drawable", getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
            canvas.drawBitmap(bitmap, entity.getX(), entity.getY(), new Paint());
        }
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


    @Override
    public void run(){
        //TESTING
        Canvas canvas = holder.lockCanvas();
        this.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
        //END TESTING
    }
}

