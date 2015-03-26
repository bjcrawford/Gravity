package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Landmark extends FixedEntity {

// Fields ------------------------------------------------------------------------------------------

// Constructors ------------------------------------------------------------------------------------

    /**
     * The default constructor for this class. Sets the unique identifier for this landmark, and initializes all other fields to zero/ empty.
     * <br>The resulting landmark will be usable by the physics logic, but will not have a useful default state.
     */
    public Landmark(int id) {
        this.position = new Point(0, 0);
        this.id = id;
        this.shapes = new ArrayList<>();
        this.shapes.add(new ArrayList<Point>());
        this.shape = this.shapes.get(0);
    }

    /**
     * The comprehensive constructor for this class. Initializes a landmark described by the input parameters.
     * @param id The unique identifier of the landmark.
     * @param position The position of the landmark.
     * @param lifespan The number of refresh cycles until the landmark will be discarded by the physics logic.
     * @param shapes A list of the possible shapes of the landmark.
     */
    public Landmark(int id, Point position, int lifespan, List<List<Point>> shapes) {
        this.position = position;
        this.shapes = shapes;
        this.shape = this.shapes.get(0);
        this.id = id;
    }

// General Public Functions ------------------------------------------------------------------------


    @Override
    public void update(float deltaT) {
        //TODO: rotation
    }

// General Private Functions -----------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------

}
