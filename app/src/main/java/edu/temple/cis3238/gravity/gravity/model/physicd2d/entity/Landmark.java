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
public class Landmark extends FixedEntity {

// Fields ------------------------------------------------------------------------------------------

    /**The objects mass.*/
    private int mass;

// Constructors ------------------------------------------------------------------------------------

    /**
     * The comprehensive constructor for this class. Initializes a landmark described by the input parameters.
     * @param id The unique identifier of the landmark.
     * @param position The position of the landmark.
     * @param dTheta The rate of rotation for the landmark.
     * @param lifespan The number of refresh cycles until the landmark will be discarded by the physics logic.
     * @param shapes A list of the possible shapes of the landmark.
     */
    public Landmark(int id, Point position, int dTheta, int lifespan, List<List<Point>> shapes) {
        this.position = position;
        this.dTheta = dTheta;
        this.lifespan = lifespan;
        this.shapes = shapes;
        this.shape = this.shapes.get(0);
        this.id = id;
    }

    /**
     * Construct the landmark from an existing JSON (sub)file.
     * @param selfAsJSON The JSON object containing the data to be loaded.
     * @throws JSONException
     */
    public Landmark(JSONObject selfAsJSON) {
        try {
            this.id = selfAsJSON.getInt("id");
            this.position = new Point(selfAsJSON.getInt("x0"), selfAsJSON.getInt("y0"));
            this.dTheta = selfAsJSON.getInt("dTheta");
            this.mass = selfAsJSON.getInt("mass");
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
        }catch(JSONException e) {
            e.printStackTrace();
        }

    }

// Public ------------------------------------------------------------------------------------------


    /**
     * Returns a JSON object representation of the entity.
     * @return A JSON object containing the data of the entity.
     */
    public JSONObject toJSON() {
        JSONObject selfAsJson = super.toJSON();
        try {
            selfAsJson.put("mass", this.mass);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return selfAsJson;
    }

    @Override
    public void update(float deltaT) {
        //TODO: rotation
    }

// General Private Functions -----------------------------------------------------------------------

// Getters and Setters -----------------------------------------------------------------------------
    public int getMass() {
        return this.mass;
    }
}
