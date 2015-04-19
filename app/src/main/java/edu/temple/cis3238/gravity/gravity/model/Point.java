package edu.temple.cis3238.gravity.gravity.model;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/26/2015
 */
public class Point {

    public float x;

    public float y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point pCpy) {
        this.x = pCpy.x;
        this.y = pCpy.y;
    }

    public void offset(float deltaX, float deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
