package edu.temple.cis3238.gravity.gravity.model.graphics2d;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.entity.Entity;

/**
 *
 * @author Brett Crawford
 * @author Ian Speers
 * @version 1.0a last modified 4/5/2015
 */
public class Graphics2D {

    /*A list of graphics entities.*/
    private List<Entity> entities;

    //TODO: Read new fields into object from JSON
    /**The height of the graphics space.*/
    private int screenHeight;

    /**The width of the graphics space.*/
    private int screenWidth;

    private double minimapScalar;

    private List<Entity> miniMap;

    //TODO: end above todo

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param graphicsJSONObject The Graphics JSONObject.
     */
    public Graphics2D(JSONObject graphicsJSONObject, int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        entities = new ArrayList<Entity>();
        try {
            JSONArray entitiesJSONArray = graphicsJSONObject.getJSONArray("entities");
            for (int i = 0; i < entitiesJSONArray.length(); i++) {
                // Refactored for O(1) access to graphics entities
                // This implementation allows for the entities matrix to be sparsely populated
                // in the case that there are unused entity ids in the level JSON.
                // If the level has been implemented efficiently, ids should occur in a contiguous set,
                // and this will not be an issue.
                Entity tmpEnt = new Entity(entitiesJSONArray.getJSONObject(i));
                entities.add(tmpEnt.getId(), tmpEnt);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of entities for the given ids.
     * @param ids The ids of the entities to return.
     * @return A list of Entity objects
     */
    public List<Entity> getListOfEntitiesByIds(List<Integer> ids) {

        // Refactored to run in O(n)
        // Where n is the length of the input list of ids
        List<Entity> entitiesResult = new ArrayList<Entity>();
        for (Integer id : ids) {
            entitiesResult.add(this.entities.get(id));
        }
        return entitiesResult;
    }

    /**
     * Get the graphics entity corresponding to the given id.
     * @param id The id of the desied graphics entity.
     * @return The graphics entity corresponding to the given id,<br>
     *     or null if the id is invalid.
     */
    public Entity getEntityByID(int id) {
        if(id < this.entities.size()) this.entities.get(id);
        return null;
    }
}
