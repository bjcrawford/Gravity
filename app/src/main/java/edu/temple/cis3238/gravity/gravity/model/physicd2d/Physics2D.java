package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Point;
import java.util.LinkedList;

import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Body;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Entity;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Landmark;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Phenomenon;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Physics2D {

// Fields ------------------------------------------------------------------------------------------

    /**An array of Region2D objects used to represent a rectangular physics environment.*/
    private Region2D[] world;

    /**Used to track the current number of entities (i.e bodies and phenomena), and to assign unique ids to new entities.*/
    private int idCtr;

    /**A collection of bodies currently active in the world*/
    private List<Body> bodies;

    /**A collection of landmarks currently active in the world*/
    private List<Landmark> landmarks;

    /**A collection of phenomena currently active in the world*/
    private List<Phenomenon> phenomena;

    /**A collection of entities which have been updated since the last call to the objects getUpdated() method.*/
    private List<Entity> updated;

// Constructors ------------------------------------------------------------------------------------

    public Physics2D() {
        this.idCtr = 0;
        this.bodies = new ArrayList<>();
        this.landmarks = new ArrayList<>();
        this.phenomena = new ArrayList<>();
        this.updated = new ArrayList<>();
    }

    // General Public Functions ------------------------------------------------------------------------

    public void createBody(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.bodies.add(new Body(id, position, dx0, dy0, lifespan, shapes));
        this.idCtr ++;
    }

    public void createLandmark(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.landmarks.add(new Landmark(id, position, lifespan, shapes));
        this.idCtr++;
    }

    public void createPhenomenon(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.phenomena.add(new Phenomenon(id, position, dx0, dy0, lifespan, shapes));
        this.idCtr ++;
    }

    /**
     * Update all of the entities in the Physics2Ds collections of entities.
     * @param deltaT The time elapsed since the last call to this objects update method.
     */
    public void update(float deltaT) {

        // Update each of the bodies.
        for(Body body : this.bodies) {
            // If the body has been updated, add it to the list of updated entities.
            if(body.update(deltaT)) {
                this.updated.add(body);
            }
        }

        // Update each of the landmarks.
        for(Landmark landmark : this.landmarks) {
            // If the landmark has been updated, add it to the list of updated entities.
            if(landmark.update(deltaT)) {
                this.updated.add(landmark);
            }
        }

        // Update each of the phenomena.
        for(Phenomenon phenomenon : this.phenomena) {
            // If the phenomenon has been updated, add it to the list of updated entities.
            if(phenomenon.update(deltaT)) {
                this.updated.add(phenomenon);
            }
        }
    }

    public boolean applyAcceleratingForceToBody(int entityID, float delta_d2x, float delta_d2y) {
        // Traverse the list of bodies to find the body with the correct id.
        for(Body body : this.bodies) {
            // If found, apply acceleration and return true.
            if(body.getId() == entityID) {
                body.applyAcceleratingForce(delta_d2x, delta_d2y);
                return true;
            }
        }
        // If the body is not found, return false.
        return false;
    }

// General Private Functions -----------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------

    public List<Integer> getUpdatedIndices() {
        List<Integer> updatedIndices = new LinkedList<>();
        // Copy the indices of the updated entities into a new list.
        for(Entity entity : this.updated) {
            updatedIndices.add(entity.getId());
        }
        return updatedIndices;
    }

}
