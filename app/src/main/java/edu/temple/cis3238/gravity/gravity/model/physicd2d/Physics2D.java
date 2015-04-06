package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.temple.cis3238.gravity.gravity.model.Point;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Body;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Entity;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Landmark;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.entity.Phenomenon;

/**
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Physics2D {

// Fields ------------------------------------------------------------------------------------------

    /**
     * Used to track the current number of entities (i.e bodies and phenomena), and to assign unique ids to new entities.
     */
    private int idCtr;

    /**
     * The universe of discourse for this physics model
     */
    private Plane2D universe;

    /**
     * A collection of bodies currently active in the world
     */
    private List<Body> bodies;

    /**
     * A collection of landmarks currently active in the world
     */
    private List<Landmark> landmarks;

    /**
     * A collection of phenomena currently active in the world
     */
    private List<Phenomenon> phenomena;

    /**Defines the strength of gravity.*/
    private int gravConstant;

// Constructors ------------------------------------------------------------------------------------

    /**
     * Instantiate a new Physics2D object with an empty world of size width x height.
     *
     * @param width  The worldWidth of the objects world.
     * @param height The worldHeight of the objects world.
     */
    public Physics2D(int width, int height) {
        this.idCtr = 0;
        this.bodies = new ArrayList<>();
        this.landmarks = new ArrayList<>();
        this.phenomena = new ArrayList<>();

        this.universe = new Plane2D(width, height);
    }

    public Physics2D(JSONObject selfAsJson) {
        this.idCtr = 0;
        try{
            this.readBodies(selfAsJson.getJSONArray("bodies"));
            this.readLandmarks(selfAsJson.getJSONArray("landmarks"));
            this.readPhenomena(selfAsJson.getJSONArray("phenomena"));
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

// Private -----------------------------------------------------------------------------------------

    /**
     * Read bodies from the JSON list.
     * @param persistent The JSON list of bodies
     */
    private void readBodies(JSONArray persistent) {
        try {
            for (int index = 0; index < persistent.length(); index++) {
                JSONObject jBody = persistent.getJSONObject(index);
                Body tmpBod = new Body(jBody);
                this.bodies.add(tmpBod);
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the landmarks from the JSON list.
     * @param persistent The JSON list of landmarks.
     */
    private void readLandmarks(JSONArray persistent) {
        try {
            for (int index = 0; index < persistent.length(); index++) {
                JSONObject jLandmark = persistent.getJSONObject(index);
                Landmark tmpLM = new Landmark(jLandmark);
                this.landmarks.add(tmpLM);
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the phenomena from the JSON list.
     * @param persistent The JSON list of phenomena.
     */
    private void readPhenomena(JSONArray persistent) {
        try {
            for (int index = 0; index < persistent.length(); index++) {
                JSONObject jPhenomenon = persistent.getJSONObject(index);
                Phenomenon tmpPh = new Phenomenon(jPhenomenon);
                this.phenomena.add(tmpPh);
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeBodies(JSONArray persistent) {
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void writeLandmarks(JSONArray persistent) {
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void writePhenomena(JSONArray persistent) {
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Initialize the gravity field of the universe (2DPlane)
     */
    private void constructUniverse() {
        for(int xNdex = 0; xNdex < this.universe.getPlaneWidth(); xNdex ++) {
            for(int yNdex = 0; yNdex < this.universe.getPlaneWidth(); yNdex ++) {
                float d2xTally = 0, d2yTally = 0;
                for(Landmark landmark : this.landmarks) {
                    int xDiff = landmark.getPosition().x - xNdex;
                    float tmpD2X = this.gravConstant * (landmark.getMass() / (xDiff * xDiff));
                    if(xDiff < 0) {
                        tmpD2X *= -1;   // Apply gravity in the appropriate direction
                    }

                    int yDiff = landmark.getPosition().y - yNdex;
                    float tmpD2Y = this.gravConstant * (landmark.getMass() / (yDiff * yDiff));
                    if(yDiff < 0) {
                        tmpD2Y *= -1;   // Apply gravity in the appropriate direction
                    }
                    this.universe.defineRegion(new Point(xNdex, yNdex), tmpD2X, tmpD2Y, -1);
                }
            }
        }
    }

    private void placeEntities() {
        //TODO: set occupancy of entities in the universe(plane2d)
    }

// Protected ---------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    public void createBody(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.bodies.add(new Body(id, position, dx0, dy0, lifespan, shapes));
        this.idCtr++;
    }

    public void createLandmark(int id, Point position, int dTheta, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.landmarks.add(new Landmark(id, position, dTheta, lifespan, shapes));
        this.idCtr++;
    }

    public void createPhenomenon(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.phenomena.add(new Phenomenon(id, position, dx0, dy0, lifespan, shapes));
        this.idCtr++;
    }

    public JSONObject toJSON() {

        // Create a new JSON object and add the entities lists to it.
        JSONObject selfAsJSON = new JSONObject();

        try {
            // Get the JSON of the bodies list
            JSONArray pBodies = new JSONArray();
            for (Body body : this.bodies) {
                pBodies.put(body.toJSON());
            }
            // Get the JSON of the landmarks list
            JSONArray pLandmarks = new JSONArray();
            for (Body body : this.bodies) {
                pLandmarks.put(body.toJSON());
            }
            // Get JSON of the phenomena list
            JSONArray pPhenomena = new JSONArray();
            for (Body body : this.bodies) {
                pPhenomena.put(body.toJSON());
            }
            selfAsJSON.put("bodies", pBodies);
            selfAsJSON.put("landmarks", pLandmarks);
            selfAsJSON.put("phenomena", pPhenomena);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return selfAsJSON;
    }

    /**
     * Update all of the entities in the Physics2Ds collections of entities.
     * @param deltaT The time elapsed since the last call to this objects update method.
     */
    public void update(float deltaT) {

        // Update each of the bodies.
        for (Body body : this.bodies) {
            Point preImagePosition = new Point(body.getPosition());
            // Remove the body's occupied space projection from the plane.
            for (Point subsection : body.getShape()) {
                // Copy each of the points in the body's shape, and translate them by the previous <x, y> offset of the body's position.
                Point translatedPoint = new Point(subsection);
                translatedPoint.offset(preImagePosition.x, preImagePosition.y);
                //Set the associated region to empty.
                this.universe.setRegionEmpty(translatedPoint);
            }

            body.update(deltaT);

            int xPos = body.getPosition().x, yPos = body.getPosition().y;
            // Add the body's occupied space projection to the plane.
            for (Point subsection : body.getShape()) {
                // Copy each of the points in the body's shape, and translate them by the current <x, y> offset of the body.
                Point translatedPoint = new Point(subsection);
                translatedPoint.offset(xPos, yPos);
                //Set the associated region to occupied.
                this.universe.setRegionOccupied(translatedPoint, body.getId());
            }
        }

        // Update each of the phenomena.
        for (Phenomenon phenomenon : this.phenomena) {
            Point preImagePosition = new Point(phenomenon.getPosition());
            // Remove the phenomenon's occupied space projection from the plane.
            for (Point subsection : phenomenon.getShape()) {
                // Copy each of the points in the phenomenon's shape, and translate them by the current <x, y> offset of the phenomenon.
                Point translatedPoint = new Point(subsection);
                translatedPoint.offset(preImagePosition.x, preImagePosition.y);
                //Set the associated region to empty.
                this.universe.setRegionEmpty(translatedPoint);
            }

            phenomenon.update(deltaT);

            int xPos = phenomenon.getPosition().x, yPos = phenomenon.getPosition().y;
            // Add the phenomenon's occupied space projection to the plane.
            for (Point subsection : phenomenon.getShape()) {
                // Copy each of the points in the phenomenon's shape, and translate them by the current <x, y> offset of the phenomenon.
                Point translatedPoint = new Point(subsection);
                translatedPoint.offset(xPos, yPos);
                //Set the associated region to occupied.
                this.universe.setRegionOccupied(translatedPoint, phenomenon.getId());
            }
        }
    }

    /**
     * Update the acceleration of the given entity.
     *
     * @param entityID  The unique id of the entity to be modified.
     * @param delta_d2x The desired differential in the x direction.
     * @param delta_d2y The desired differential in the y direction.
     * @return A boolean signifying weather the entity was modified.
     */
    public boolean applyAcceleratingForceToBody(int entityID, float delta_d2x, float delta_d2y) {
        // Traverse the list of bodies to find the body with the correct id.
        for (Body body : this.bodies) {
            // If found, apply acceleration and return true.
            if (body.getId() == entityID) {
                body.applyAcceleratingForce(delta_d2x, delta_d2y);
                return true;
            }
        }
        // If the body is not found, return false.
        return false;
    }

    /**
     * Get the entity associated with the given id.
     * @param entityID The id of the desired entity.
     * @return
     */
    public Entity getEntity(int entityID) {
        // Traverse the list of bodies to find the body with the correct id.
        for (Body body : this.bodies) {
            // If found, apply acceleration and return true.
            if (body.getId() == entityID) {
                return body;
            }
        }
        return null;
    }

    /**
     * Get a list of entities present in the given region.
     * @param center The center of the region of observation.
     * @param xDiff The limit of observation in the x direction;<br>
     *              i.e range = [center.x - xDiff, frameCenter.x + xDiff]
     * @param yDiff The limit of observation in the x direction;<br>
     *              i.e range = [center.x - xDiff, frameCenter.x + xDiff]
     * @return A list of entities found in the specified region.
     */
    public List<Entity> observe(Point center, int xDiff, int yDiff) {
        List<Entity> subjects = new ArrayList<Entity>();
        //TODO: get entities in region
        return subjects;
    }

// Getters and Setters -----------------------------------------------------------------------------

    public List<Landmark> getLandmarks() {
        return this.landmarks;
    }
}
