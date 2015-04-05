package edu.temple.cis3238.gravity.gravity.model;

/**
 *
 * @author Ian Speers
 * @version 1.0a last modified 4/5/2015
 */
public class ImageResourceWrapper {

    /**The on-screen position to which the image should be drawn.*/
    public Point position;

    /**The resource id of the image to be drawn.*/
    public String imgResID;

    /**
     * Constructs a bundle od information needed to display an image at the appropriate location.
     * @param position The on-screen position to which the image should be drawn.
     * @param imgResID The resource id of the image to be drawn.
     */
    public ImageResourceWrapper(Point position, String imgResID) {
        this.position = position;
        this.imgResID = imgResID;
    }

}
