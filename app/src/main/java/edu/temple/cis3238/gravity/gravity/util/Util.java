package edu.temple.cis3238.gravity.gravity.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.activity.PlayGameActivity;
import edu.temple.cis3238.gravity.gravity.model.Point;

public class Util {

    public static List<List<Point>> getShapes(List<String> imgResIds) {
        List<List<Point>> shapes = new ArrayList<List<Point>>();

        for (String imgResId : imgResIds) {
            Bitmap bitmap = BitmapFactory.decodeResource(
                    App.getContext().getResources(),
                    App.getContext().getResources().getIdentifier(imgResId,
                    "drawable", App.getContext().getPackageName()));
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int pppg = PlayGameActivity.PIXELS_PER_PHYSICS_GRID;
            List<Point> shape = new ArrayList<Point>();
            for (int y = 0; y < height; y += pppg) {
                for (int x = 0; x < width; x += pppg) {
                    if ((bitmap.getPixel(x, y) >> 24) != 0x00) {
                        int shapeX = (x - width / 2) / pppg;
                        int shapeY = (y - height / 2) / pppg;
                        shape.add(new Point(shapeX, shapeY));
                    }
                }
            }
            shapes.add(shape);
        }

        return shapes;
    }
}
