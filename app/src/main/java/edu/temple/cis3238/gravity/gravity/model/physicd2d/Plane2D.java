package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import android.graphics.Point;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Plane2D {

// Fields ------------------------------------------------------------------------------------------

    /**An array of Region2D objects used to represent a rectangular physics environment.*/
    private Region2D[] plane;

    /**The worldWidth of the plane. Used to map 2D coordinates into the 1D plane array.*/
    private int planeWidth;

    /**The worldHeight of the world. Used to map 2D coordinates into the 1D world array.*/
    private int planeHeight;

// Constructors ------------------------------------------------------------------------------------

    public Plane2D(int width, int height) {
        this.planeWidth = width;
        this.planeHeight = height;
        this.plane = new Region2D[width * height];
    }

// General Public Functions ------------------------------------------------------------------------

    /**
     * Define the region at the position specified.
     * @param position The position of the region to bedefined.
     * @param region The data of the region.
     */
    public void defineRegion(Point position, Region2D region) {
        this.worldHashPutRegion2D(position, region);
    }

    /**
     * Access the data of the region at the given position.
     * @param position The position of the region to be accessed.
     * @return The data of the desired region.
     */
    public Region2D accessRegion(Point position) {
        return this.worldHashGetRegion2D(position);
    }

    /**
     * Set the given regions occupant identifier to empty.
     * @param position The position of the region to be modified.
     */
    public void setRegionEmpty(Point position) {
        this.worldHashGetRegion2D(position).setOccupantID(-1);
    }

    /**
     * Set the given regions occupant identifier to the given ID.
     * @param position The position of the region to be modified.
     * @param occupantID The desired id value for the specified region.
     */
    public void setRegionOccupied(Point position, int occupantID) {
        this.worldHashGetRegion2D(position).setOccupantID(occupantID);
    }

// General Private Functions -----------------------------------------------------------------------

    /**
     * Returns the Region2D corresponding to the given position in the objects *logical* 2D space.
     * @param position The 2D coordinates of the desired Region2D.
     * @return The Region2D housed at the location specified by the input parameter.
     */
    private Region2D worldHashGetRegion2D(Point position) {
        // If the given point lies within the boundaries of the world.
        if(position.x <= this.planeWidth && position.y <= this.planeHeight && position.x >= 0 && position.y >= 0) {
            // Calculate the 1D index corresponding to the given 2D point.
            int packedIndex = (this.planeWidth * position.y) + position.x;
            return this.plane[packedIndex];
        }else {
            return null;
        }
    }

    /**
     * Populate the region given by the input point with the data of the specified Region2D.
     * @param position The 2D location of the region to be modified.
     * @param region The data to be stored at the given location.
     */
    private void worldHashPutRegion2D(Point position, Region2D region) {
        // If the given point lies within the boundaries of the world.
        if(position.x <= this.planeWidth && position.y <= this.planeHeight && position.x >= 0 && position.y >= 0) {
            // Calculate the 1D index corresponding to the given 2D point.
            int packedIndex = (this.planeWidth * position.y) + position.x;
            this.plane[packedIndex] = region;
        }
    }

// Getters and Setters -----------------------------------------------------------------------------

    public int getPlaneWidth() {
        return this.planeWidth;
    }

    public int getPlaneHeight() {
        return this.planeHeight;
    }

}
