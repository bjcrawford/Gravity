package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Phenomenon extends MobileEntity {

// Fields ------------------------------------------------------------------------------------------

// Constructors ------------------------------------------------------------------------------------

    /**
     * The default constructor for this class. Sets the unique identifier for this phenomenon, and initializes all other fields to zero/ empty.
     * <br>The resulting phenomenon will be usable by the physics logic, but will not have a useful default state.
     */
    public Phenomenon(int id) {
        this.position = new Point(0, 0);
        this.dx = 0;
        this.dy = 0;
        this.d2x = 0;
        this.d2y = 0;
        this.id = id;
        this.shapes = new ArrayList<>();
        this.shapes.add(new ArrayList<Point>());
    }

    /**
     * The comprehensive constructor for this class. Initializes a phenomenon described by the input parameters.
     * @param id The unique identifier of the phenomenon.
     * @param position The starting position of the phenomenon.
     * @param dx0 The initial x velocity of the phenomenon.
     * @param dy0 The initial y velocity of the phenomenon.
     * @param lifespan The number of refresh cycles until the phenomenon will be discarded by the physics logic.
     * @param shapes A list of the possible shapes of this phenomenon.
     */
    public Phenomenon(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.position = position;
        this.dx = dx0;
        this.dy = dy0;
        this.d2x = 0;
        this.d2y = 0;
        this.shapes = shapes;
        this.id = id;
    }

// General Public Functions ------------------------------------------------------------------------

    @Override
    public void applyAcceleratingForce(float delta_d2x, float delta_d2y) {
        this.d2x += delta_d2x;
        this.d2y += delta_d2y;
    }

    @Override
    public boolean update(float deltaT) {
        this.accelerate(deltaT);
        this.displace(deltaT);
        //TODO: rotation
        return  true; //TODO: decide if phenomenon has been modified
    }

// General Private Functions -----------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------

}
