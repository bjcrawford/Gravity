package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import org.json.JSONObject;

import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/26/2015
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

    /**
     * Initialize the plane to the given height and width.
     * @param width The desired width of the plane (number of regions).
     * @param height The desired height of the plane (number of regions).
     */
    public Plane2D(int width, int height) {
        this.planeWidth = width;
        this.planeHeight = height;
        this.plane = new Region2D[width * height];
    }

    /**
     * Construct a plane from an existing list of Region2D objects.
     * @param width The desired width of the plane.
     * @param regions The list of regions with which to populate the plane.
     */
    public Plane2D(int width, List<Region2D> regions) {
        this.planeWidth = width;
        this.planeHeight = (int) (regions.size() / width);
        this.plane = new Region2D[this.planeWidth * this.planeHeight];
        this.plane = regions.subList(0, this.planeHeight * this.planeWidth).toArray(this.plane);
    }

// Private -----------------------------------------------------------------------------------------

    /**
     * Returns the Region2D corresponding to the given position in the objects *logical* 2D space.
     * @param position The 2D coordinates of the desired Region2D.
     * @return The Region2D housed at the location specified by the input parameter.
     * <br> Returns null if the specified region is outside of the bounds of the plane.
     */
    private Region2D worldHashGetRegion2D(Point position) {
        // If the given point lies within the boundaries of the world.
        if(position.x < this.planeWidth && position.y < this.planeHeight && position.x >= 0 && position.y >= 0) {
            // Calculate the 1D index corresponding to the given 2D point.
            int packedIndex = (this.planeWidth * position.y) + position.x;
            // If the desired region has not been initialized, Instantiate a new Region2D object with default constructor.
            if(this.plane[packedIndex] == null) {
                this.plane[packedIndex] = new Region2D();
            }
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
        if(position.x < this.planeWidth && position.y < this.planeHeight && position.x >= 0 && position.y >= 0) {
            // Calculate the 1D index corresponding to the given 2D point.
            int packedIndex = (this.planeWidth * position.y) + position.x;
            this.plane[packedIndex] = region;
        }
    }


// Protected ---------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    /**
     * Define the region at the position specified.
     * @param position The position of the region to be defined.
     * @param d2x The acceleration in the x direction experienced at the given region.
     * @param d2y The acceleration in the y direction experienced at the given region.
     * @param occupantID The unique id of the occupant of the given region.
     */
    public void defineRegion(Point position, float d2x, float d2y, int occupantID) {
        Region2D region = new Region2D(d2x, d2y, occupantID);
        if(position.x < this.planeWidth && position.y < this.planeHeight && position.x >= 0 && position.y >= 0) {
            this.worldHashPutRegion2D(position, region);
        }
    }

    /**
     * Access the data of the region at the given position.
     * @param position The position of the region to be accessed.
     * @return The data of the desired region.
     * <br> Returns null if the specified region is outside of the bounds of the plane.
     */
    public Region2D accessRegion(Point position) {
        if(position.x < this.planeWidth && position.y < this.planeHeight && position.x >= 0 && position.y >= 0) {
            return this.worldHashGetRegion2D(position);
        }
        else {
            return null;
        }
    }

    /**
     * Set the given regions occupant identifier to empty.
     * @param position The position of the region to be modified.
     */
    public void setRegionEmpty(Point position) {
        if(position.x < this.planeWidth && position.y < this.planeHeight && position.x >= 0 && position.y >= 0) {
            this.worldHashGetRegion2D(position).setOccupantID(-1);
        }
    }

    /**
     * Set the given regions occupant identifier to the given ID.
     * @param position The position of the region to be modified.
     * @param occupantID The desired id value for the specified region.
     */
    public void setRegionOccupied(Point position, int occupantID) {
        if(position.x < this.planeWidth && position.y < this.planeHeight && position.x >= 0 && position.y >= 0) {
            this.worldHashGetRegion2D(position).setOccupantID(occupantID);
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
