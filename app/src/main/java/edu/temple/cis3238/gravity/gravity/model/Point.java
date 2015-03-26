package edu.temple.cis3238.gravity.gravity.model;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/26/2015
 */
public class Point {

    public int x;

    public int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point pCpy) {
        this.x = pCpy.x;
        this.y = pCpy.y;
    }

    public void offset(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}
