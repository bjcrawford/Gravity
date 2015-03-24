package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /**Used to track the current number of entities (i.e bodies and phenomena), and to assign unique ids to new entities.*/
    private int idCtr;

    /**The universe of discourse for this physics model*/
    private Plane2D unverse;

    /**A collection of bodies currently active in the world*/
    private List<Body> bodies;

    /**A collection of landmarks currently active in the world*/
    private List<Landmark> landmarks;

    /**A collection of phenomena currently active in the world*/
    private List<Phenomenon> phenomena;

    /**A collection of entities which have been updated since the last call to the objects getUpdated() method.*/
    private List<Entity> updated;

// Constructors ------------------------------------------------------------------------------------

    /**
     * Instantiate a new Physics2D object with an empty world of size width x height.
     * @param width The worldWidth of the objects world.
     * @param height The worldHeight of the objects world.
     */
    public Physics2D(int width, int height) {
        this.idCtr = 0;
        this.bodies = new ArrayList<>();
        this.landmarks = new ArrayList<>();
        this.phenomena = new ArrayList<>();
        this.updated = new ArrayList<>();

        this.unverse = new Plane2D(width, height);
    }

    public Physics2D(JSONObject persistent) throws JSONException {
        this.idCtr = 0;
        this.readBodies(persistent.getJSONArray("bodies"));
        this.readLandmarks(persistent.getJSONArray("landmarks"));
        this.readPhenomena(persistent.getJSONArray("phenomena"));
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

    public void defineRegion(Point position, float d2xGrav, float d2yGrav) {
        this.unverse.defineRegion(position, new Region2D(d2xGrav, d2yGrav));
    }

    public JSONObject toJSON() throws JSONException{
        // Get the JSON of the bodies list
        JSONArray pBodies = new JSONArray();
        for(Body body : this.bodies) {
            pBodies.put(body.toJSON());
        }
        // Get the JSON of the landmarks list
        JSONArray pLandmarks = new JSONArray();
        for(Body body : this.bodies) {
            pLandmarks.put(body.toJSON());
        }
        // Get JSON of the phenomena list
        JSONArray pPhenomena = new JSONArray();
        for(Body body : this.bodies) {
            pPhenomena.put(body.toJSON());
        }
        // Create a new JSON object and add the entities lists to it.
        JSONObject selfAsJSON = new JSONObject();
        selfAsJSON.put("bodies", pBodies);
        selfAsJSON.put("landmarks", pLandmarks);
        selfAsJSON.put("phenomena", pPhenomena);
        return selfAsJSON;
    }

    /**
     * Update all of the entities in the Physics2Ds collections of entities.
     * @param deltaT The time elapsed since the last call to this objects update method.
     */
    public void update(float deltaT) {

        // Update each of the bodies.
        for(Body body : this.bodies) {
            Point preImagePosition = new Point(body.getPosition());
            // If the body has been updated, add it to the list of updated entities.
            if(body.update(deltaT)) {
                // TODO: Accelerate bodies
                this.updated.add(body);
                // Remove the body's occupied space projection from the plane.
                for(Point subsection : body.getShape()) {
                    // Copy each of the points in the body's shape, and translate them by the previous <x, y> offset of the body's position.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(preImagePosition.x, preImagePosition.y);
                    //Set the associated region to empty.
                    this.unverse.setRegionEmpty(translatedPoint);
                }

                int xPos = body.getPosition().x, yPos = body.getPosition().y;
                // Add the body's occupied space projection to the plane.
                for(Point subsection : body.getShape()) {
                    // Copy each of the points in the body's shape, and translate them by the current <x, y> offset of the body.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(xPos, yPos);
                    //Set the associated region to occupied.
                    this.unverse.setRegionOccupied(translatedPoint, body.getId());
                }
            }
        }

        // Update each of the landmarks.
        for(Landmark landmark : this.landmarks) {
            Point preImagePosition = new Point(landmark.getPosition());
            // If the landmark has been updated, add it to the list of updated entities.
            if(landmark.update(deltaT)) {
                this.updated.add(landmark);
                // Remove the landmark's occupied space projection from the plane.
                for(Point subsection : landmark.getShape()) {
                    // Copy each of the points in the landmark's shape, and translate them by the current <x, y> offset of the landmark.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(preImagePosition.x, preImagePosition.y);
                    //Set the associated region to empty.
                    this.unverse.setRegionEmpty(translatedPoint);
                }
                int xPos = landmark.getPosition().x, yPos = landmark.getPosition().y;
                // Add the landmark's occupied space projection to the plane.
                for(Point subsection : landmark.getShape()) {
                    // Copy each of the points in the landmark's shape, and translate them by the current <x, y> offset of the landmark.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(xPos, yPos);
                    //Set the associated region to occupied.
                    this.unverse.setRegionOccupied(translatedPoint, landmark.getId());
                }
            }
        }

        // Update each of the phenomena.
        for(Phenomenon phenomenon : this.phenomena) {
            // TODO: Accelerate phenomena.
            Point preImagePosition = new Point(phenomenon.getPosition());
            // If the phenomenon has been updated, add it to the list of updated entities.
            if(phenomenon.update(deltaT)) {
                this.updated.add(phenomenon);
                // Remove the phenomenon's occupied space projection from the plane.
                for(Point subsection : phenomenon.getShape()) {
                    // Copy each of the points in the phenomenon's shape, and translate them by the current <x, y> offset of the phenomenon.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(preImagePosition.x, preImagePosition.y);
                    //Set the associated region to empty.
                    this.unverse.setRegionEmpty(translatedPoint);
                }
                int xPos = phenomenon.getPosition().x, yPos = phenomenon.getPosition().y;
                // Add the phenomenon's occupied space projection to the plane.
                for(Point subsection : phenomenon.getShape()) {
                    // Copy each of the points in the phenomenon's shape, and translate them by the current <x, y> offset of the phenomenon.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(xPos, yPos);
                    //Set the associated region to occupied.
                    this.unverse.setRegionOccupied(translatedPoint, phenomenon.getId());
                }
            }
        }
    }

    /**
     * Update the acceleration of the given entity.
     * @param entityID The unique id of the entity to be modified.
     * @param delta_d2x The desired differential in the x direction.
     * @param delta_d2y The desired differential in the y direction.
     * @return A boolean signifying weather the entity was modified.
     */
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

    private void readBodies(JSONArray persistent) throws JSONException{
        for(int index = 0; index < persistent.length(); index ++) {
            JSONObject pBody = persistent.getJSONObject(index);
            //TODO: body.toJSON()
        }
    }

    private void readLandmarks(JSONArray persistent) throws JSONException{
        for(int index = 0; index < persistent.length(); index ++) {
            JSONObject pLandmark = persistent.getJSONObject(index);
            //TODO: landmark.toJSON()
        }
    }

    private void readPhenomena(JSONArray persistent) throws JSONException{
        for(int index = 0; index < persistent.length(); index ++) {
            JSONObject pPhenomenon = persistent.getJSONObject(index);
            //TODO: phenomenon.toJSON()
        }
    }



// Getters and Setters -----------------------------------------------------------------------------

    public List<Integer> getUpdatedIndices() {
        List<Integer> updatedIndices = new LinkedList<>();
        // Copy the indices of the updated entities into a new list.
        for(Entity entity : this.updated) {
            updatedIndices.add(entity.getId());
        }
        this.updated.clear();
        return updatedIndices;
    }

}
