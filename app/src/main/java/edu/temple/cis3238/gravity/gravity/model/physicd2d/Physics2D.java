package edu.temple.cis3238.gravity.gravity.model.physicd2d;

import android.util.Log;

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
 * @version 1.0a last modified 4/6/2015
 */
public class Physics2D {

    private static final String TAG = "Physics2D";

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

    private ArrayList<Entity> entitiesLookupTable;

    /**Defines the strength of gravity.*/
    private float gravConstant;

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
        this.gravConstant = 0.000001f;

        this.universe = new Plane2D(width, height);
    }

    /**
     * Construct a physics object from the given JSON file.
     * @param selfAsJson A JSON representation of the desired physics object.
     */
    public Physics2D(JSONObject selfAsJson, int width, int height) {
        this.idCtr = 0;
        this.bodies = new ArrayList<>();
        this.landmarks = new ArrayList<>();
        this.phenomena = new ArrayList<>();
        this.universe = new Plane2D(width, height);
        this.gravConstant = 0.000001f;
        try{
            this.readBodies(selfAsJson.getJSONArray("bodies"));
            this.readLandmarks(selfAsJson.getJSONArray("landmarks"));
            this.readPhenomena(selfAsJson.getJSONArray("phenomena"));
        }catch(JSONException e) {
            e.printStackTrace();
        }
        this.constructUniverse();
        this.constructEntitiesLookupTable();
        this.placeEntities();
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

    private void writeBodies() {
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void writeLandmarks() {
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void writePhenomena() {
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
    }

    private boolean isPointContained(Point p, Point center, int xDiff, int yDiff) {
        if(p.x < center.x - xDiff || p.x > center.x + xDiff) return false;
        if(p.y < center.y - yDiff || p.y > center.y + yDiff) return false;
        return true;
    }

    /**
     * Initialize a hasmap of entities, which will allow for O(1) access to entities by id.
     */
    private void constructEntitiesLookupTable() {
        this.entitiesLookupTable = new ArrayList<>();
        for(Body body : this.bodies) {
            this.entitiesLookupTable.add(body.getId(), body);
        }
        for(Landmark landmark : this.landmarks) {
            this.entitiesLookupTable.add(landmark.getId(), landmark);
        }
        for(Phenomenon phenomenon : this.phenomena) {
            this.entitiesLookupTable.add(phenomenon.getId(), phenomenon);
        }
    }

    /**
     * Initialize the gravity field of the universe (2DPlane)
     * O(m*n) where:
     *      - n is the number of regions in the universe.
     *      - m is the number of landmarks in the universe.
     * With considerable constant cost at each iteration.
     */
    private void constructUniverse() {

        Log.d(TAG, "Gravity Constant: " + gravConstant);

        for(int xNdex = 0; xNdex < this.universe.getPlaneWidth(); xNdex ++) {
            for(int yNdex = 0; yNdex < this.universe.getPlaneWidth(); yNdex ++) {
                float d2xTally = 0, d2yTally = 0;
                for(Landmark landmark : this.landmarks) {
                    /** The following calculations assume that the mass of the player is negligible
                     * relative to the masses of the landmarks (i.e planets).
                     *
                     * F = GM/(r^2) = GM/(sqrt(xDiff^2 + yDiff^2)^2) = GM/((xDiff^2 + yDiff^2))
                     * sin(a) = (yDiff / r) && sin(a) = (Fy / F) => Fy = (F * (yDiff / r))
                     * sin(b) = (xDiff / r) && sin(b) = (Fx / F) => Fx = (F * (xDiff / r))
                     * P* : Player
                     * M* : Mass
                     *
                     *                          P*
                     *                        /
                     *        F             / |
                     *      /             /(b_|
                     *     V            /     |
                     *                /       |           Fy
                     *          r   /         | yDiff     |
                     *            /           |           V
                     *          /             |
                     *        /               |
                     *      /                 |
                     *    /)                 _|
                     *   __a)_______________|
                     * M*         xDiff
                     *
                     *           <- Fx
                    */
                    int xDiff = landmark.getPosition().x - xNdex;
                    int yDiff = landmark.getPosition().y - yNdex;
                    int r = (int) Math.sqrt((double) ((xDiff * xDiff) + (yDiff * yDiff)));      // sqrt(a^2 + b^2)
                    float fGrav = 0;
                    if(r != 0) {
                        fGrav = ((float)(this.gravConstant * landmark.getMass())) / ((float)(r * r));   // F = GM/r^2
                    }

                    float tmpD2X = 0;
                    if(r != 0){
                        tmpD2X = fGrav * ((float)(Math.abs(yDiff)) / ((float)r));
                    }

                    if(xDiff < 0) {     // If the landmark is "behind" the region...
                        tmpD2X *= -1;   // Gravity should pull backwards.
                    }

                    float tmpD2Y = 0;
                    if(r != 0){
                        tmpD2Y = fGrav * ((float)Math.abs(xDiff) / ((float)r));
                    }

                    if(yDiff < 0) {     // If the landmark is "below" the region...
                        tmpD2Y *= -1;   // Gravity should pull downwards.
                    }
                    d2xTally += tmpD2X;
                    d2yTally += tmpD2Y;
                }
                this.universe.defineRegion(new Point(xNdex, yNdex), d2xTally, d2yTally, -1);
            }
        }
    }

    private void placeEntities() {
        // For each entity in the universe...
        for(Entity entity : this.entitiesLookupTable) {
            // For each subsection of that entity, relative to the entitys center...
            for(Point subsection : entity.getShape()) {
                // Calculate that subsections absolute location.
                Point translatedSubsection = new Point(subsection);
                translatedSubsection.offset(entity.getPosition().x, entity.getPosition().y);
                // Set the region associated with that subsection to occupied
                this.universe.setRegionOccupied(translatedSubsection, entity.getId());
            }
        }
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

        Log.d(TAG, "Time delta " + deltaT);

        // Update each of the bodies.
        for (Body body : this.bodies) {

            // Player center out of bounds
            if (body.getId() == 0 && (body.getPosition().x < 0 || body.getPosition().x >= universe.getPlaneWidth() ||
                        body.getPosition().y < 0 || body.getPosition().y >= universe.getPlaneHeight())) {
                Log.d(TAG, "Collision with level bounds detected  " + body.getPosition());
                Log.d(TAG, "Plane bounds: " + universe.getPlaneWidth() + " x " + universe.getPlaneHeight());
                body.setLifespan(0);
            }
            else {

                Point preImagePosition = new Point(body.getPosition());
                // Remove the body's occupied space projection from the plane.
                for (Point subsection : body.getShape()) {
                    // Copy each of the points in the body's shape, and translate them by the previous <x, y> offset of the body's position.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(preImagePosition.x, preImagePosition.y);
                    //Set the associated region to empty.
                    this.universe.setRegionEmpty(translatedPoint);
                }

                // Check that the body is still within the bounds of the universe
                if(body.getPosition().x >=0
                        && body.getPosition().x < this.universe.getPlaneWidth()
                        && body.getPosition().y >= 0
                        && body.getPosition().y < this.universe.getPlaneHeight()) {
                    Region2D tempRegion = this.universe.accessRegion(body.getPosition());       // If it is, apply gravity
                    body.applyAcceleratingForce(tempRegion.getD2xGrav(), tempRegion.getD2yGrav());
                    Log.d(TAG, "  Grav Region d2x: " + tempRegion.getD2xGrav() + ", d2y: " + tempRegion.getD2yGrav());
                }

                body.update(deltaT);

                int xPos = body.getPosition().x, yPos = body.getPosition().y;
                // Add the body's occupied space projection to the plane.
                for (Point subsection : body.getShape()) {
                    // Copy each of the points in the body's shape, and translate them by the current <x, y> offset of the body.
                    Point translatedPoint = new Point(subsection);
                    translatedPoint.offset(xPos, yPos);

                    // Check for player collision
                    if (body.getId() == 0 && this.universe.accessRegion(translatedPoint) != null) {
                        int occId = this.universe.accessRegion(translatedPoint).getOccupantID();
                        if (occId != -1) {
                            // Player has collided with an entity
                            Log.d(TAG, "Collision with id " + occId + " detected @ " + translatedPoint);
                            body.setLifespan(0);
                        }
                    }

                    //Set the associated region to occupied.
                    this.universe.setRegionOccupied(translatedPoint, body.getId());
                }
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

            // Check that the phenomenon is still within the bounds of the universe
            if(phenomenon.getPosition().x >=0
                    && phenomenon.getPosition().x < this.universe.getPlaneWidth()
                    && phenomenon.getPosition().y >= 0
                    && phenomenon.getPosition().y < this.universe.getPlaneHeight()) {
                Region2D tempRegion = this.universe.accessRegion(phenomenon.getPosition());     // If so, apply gravity
                phenomenon.applyAcceleratingForce(tempRegion.getD2xGrav(), tempRegion.getD2yGrav());
            }

            int xPos = phenomenon.getPosition().x, yPos = phenomenon.getPosition().y;
            // Add the phenomenon's occupied space projection to the plane.
            for (Point subsection : phenomenon.getShape()) {
                // Copy each of the points in the phenomenon's shape, and translate them by the current <x, y> offset of the phenomenon.
                Point translatedPoint = new Point(subsection);
                translatedPoint.offset(xPos, yPos);

                // TODO: Check for phenomenon collisions

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
        // Get the entity with the given id.
        Entity entWithID = this.getEntity(entityID);
        // If the id corresponds to an invalid entity, return false.
        if(entWithID == null) return false;
        // If the entity is a body, apply acceleration.
        if(entWithID instanceof Body){
            ((Body) entWithID).applyAcceleratingForce(delta_d2x, delta_d2y);
            return true;
        // If the entity is a phenomenon, apply acceleration.
        }else if(entWithID instanceof Phenomenon) {
            ((Phenomenon) entWithID).applyAcceleratingForce(delta_d2x, delta_d2y);
            return true;
        }

        // If acceleration can not be applied to the entity, return false.
        return false;
    }

    /**
     * Get the entity associated with the given id.
     * @param entityID The id of the desired entity.
     * @return
     */
    public Entity getEntity(int entityID) {
        Entity rtrnEnt = null;
        if(entityID < this.entitiesLookupTable.size()) {
            rtrnEnt = this.entitiesLookupTable.get(entityID);
        }
        return rtrnEnt;
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
        // For each entity in the universe...
        for(Entity entity : this.entitiesLookupTable) {
            // For each subsection of that entity, relative to the entitys center...
            for(Point subsection : entity.getShape()) {
                // Calculate that subsections absolute location.
                Point translatedSubsection = new Point(subsection);
                translatedSubsection.offset(entity.getPosition().x, entity.getPosition().y);
                // If the translated point is within the bounds of observation...
                if(this.isPointContained(translatedSubsection, center, xDiff, yDiff)) {
                    // Add the entity to the list of observed subjects...
                    subjects.add(entity);
                    // And break to prevent duplicate observations.
                    break;
                }
            }
        }
        return subjects;
    }

// Getters and Setters -----------------------------------------------------------------------------

    public List<Landmark> getLandmarks() {
        return this.landmarks;
    }

    /**
     * Set the gravity constant.
     *
     * @param gravConstant
     */
    public void setGravConstant(int gravConstant) {
        this.gravConstant = gravConstant;
    }

    public Point getUniverseDimensions(){
        return new Point(this.universe.getPlaneWidth(), this.universe.getPlaneHeight());
    }

    public List<Entity> getEntities() {
        return this.entitiesLookupTable;
    }
}
