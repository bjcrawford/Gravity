package edu.temple.cis3238.gravity.gravity.model.physicd2d.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class Body extends MobileEntity {

// Fields ------------------------------------------------------------------------------------------

// Constructors ------------------------------------------------------------------------------------

    /**
     * The default constructor for this class. Sets the unique identifier for this body, and initializes all other fields to zero/ empty.
     * <br>The resulting Body will be usable by the physics logic, but will not have a useful default state.
     */
    public Body(int id) {
        this.position = new Point(0, 0);
        this.dx = 0;
        this.dy = 0;
        this.d2x = 0;
        this.d2y = 0;
        this.id = id;
        this.lifespan = Integer.MAX_VALUE;
        this.shapes = new ArrayList<>();
        this.shapes.add(new ArrayList<Point>());
        this.shape = this.shapes.get(0);
    }

    /**
     * The comprehensive constructor for this class. Initializes a Body described by the input parameters.
     * @param id The unique identifier of the body.
     * @param position The starting position of the body.
     * @param dx0 The initial x velocity of the body.
     * @param dy0 The initial y velocity of the body.
     * @param lifespan The number of refresh cycles until the body will be discarded by the physics logic.<br>
     *                 If the value passed is negative, the body will be assigned an infinite lifespan.
     * @param shapes A list of possible shapes of this body.
     */
    public Body(int id, Point position, int dx0, int dy0, int lifespan, List<List<Point>> shapes) {
        this.position = position;
        this.dx = dx0;
        this.dy = dy0;
        this.d2x = 0;
        this.d2y = 0;
        if(lifespan < 0) {
            this.lifespan = Integer.MAX_VALUE;
        }else {
            this.lifespan = lifespan;
        }
        this.shapes = shapes;
        this.shape = this.shapes.get(0);
        this.id = id;
    }

    /**
     * Build the body from an existing JSON (sub)file.
     * @param selfAsJSON The JSON object containing the data to be loaded.
     * @throws JSONException
     */
    public Body(JSONObject selfAsJSON) {
        try {
            this.id = selfAsJSON.getInt("id");
            this.position = new Point(selfAsJSON.getInt("x0"), selfAsJSON.getInt("y0"));
            this.dx = selfAsJSON.getInt("dx0");
            this.dy = selfAsJSON.getInt("dy0");
            this.d2x = 0;
            this.d2y = 0;
            if(selfAsJSON.getInt("lifespan") < 0) {
                this.lifespan = Integer.MAX_VALUE;
            }else {
                this.lifespan = selfAsJSON.getInt("lifespan");
            }
            // Read Shapes list<list> out from json object.
            this.shapes = new ArrayList<>();
            JSONArray jShapes = selfAsJSON.getJSONArray("shapes");
            // For each shape in the list of shapes.
            for(int index = 0; index < jShapes.length(); index ++) {
                // Create a new point list.
                JSONArray jShape = jShapes.getJSONArray(index);
                ArrayList<Point> loadedShape = new ArrayList<>();
                // Add each point in the current shape to the new point list.
                for(int jndex = 0; jndex < jShape.length(); jndex ++) {
                    JSONObject jPoint = jShape.getJSONObject(jndex);
                    loadedShape.add(new Point(jPoint.getInt("x"), jPoint.getInt("y")));
                }
                // Append the new point list to this.shapes.
                this.shapes.add(loadedShape);
            }
            this.shape = this.shapes.get(this.calcOrientation());
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

// Private -----------------------------------------------------------------------------------------

// Protected ---------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    @Override
    public void applyAcceleratingForce(float delta_d2x, float delta_d2y) {
        this.d2x += delta_d2x;
        this.d2y += delta_d2y;
    }

    @Override
    public void update(float deltaT) {
        this.accelerate(deltaT);
        this.displace(deltaT);
        this.shape = this.shapes.get(this.calcOrientation());
    }

    /**
     * Returns a JSON object representation of the entity.
     * @return A JSON object containing the data of the entity.
     * @throws JSONException
     */
    public JSONObject toJSON() {
        return super.toJSON();
    }

// Getters and Setters -----------------------------------------------------------------------------

}
