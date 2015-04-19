package edu.temple.cis3238.gravity.gravity.model.graphics2d;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.graphics2d.graphicsentity.GraphicsEntity;

/**
 *
 * @author Brett Crawford
 * @author Ian Speers
 * @version 1.0a last modified 4/5/2015
 */
public class Graphics2D {

    /*A list of graphics entities.*/
    private List<GraphicsEntity> entities;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param graphicsJSONObject The Graphics JSONObject.
     */
    public Graphics2D(JSONObject graphicsJSONObject) {
        entities = new ArrayList<GraphicsEntity>();
        try {
            JSONArray entitiesJSONArray = graphicsJSONObject.getJSONArray("entities");
            for (int i = 0; i < entitiesJSONArray.length(); i++) {
                // Refactored for O(1) access to graphics entities
                // This implementation allows for the entities matrix to be sparsely populated
                // in the case that there are unused entity ids in the level JSON.
                // If the level has been implemented efficiently, ids should occur in a contiguous set,
                // and this will not be an issue.
                GraphicsEntity tmpEnt = new GraphicsEntity(entitiesJSONArray.getJSONObject(i));
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
    public List<GraphicsEntity> getListOfEntitiesByIds(List<Integer> ids) {

        // Refactored to run in O(n)
        // Where n is the length of the input list of ids
        List<GraphicsEntity> entitiesResult = new ArrayList<GraphicsEntity>();
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
    public GraphicsEntity getEntityByID(int id) {
        if(id < this.entities.size()) return this.entities.get(id);
        return null;
    }

    public JSONObject toJSON() {
        JSONArray jEntities = new JSONArray();
        for(GraphicsEntity ent : this.entities) {
            jEntities.put(ent.toJSON());
        }
        JSONObject selfAsJSON = new JSONObject();

        try{
            selfAsJSON.put("entities", jEntities);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return selfAsJSON;
    }
}
