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
 * @version 1.0a last modified 3/29/2015
 */
public class Graphics2D {

    /*A list of graphics entities.*/
    private List<Entity> entities;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param graphicsJSONObject The Graphics JSONObject.
     */
    public Graphics2D(JSONObject graphicsJSONObject) {
        entities = new ArrayList<Entity>();
        try {
            JSONArray entitiesJSONArray = graphicsJSONObject.getJSONArray("entities");
            for (int i = 0; i < entitiesJSONArray.length(); i++) {
                entities.add(i, new Entity(entitiesJSONArray.getJSONObject(i)));
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

        // This is a really inefficient implementation, but can be
        // addressed in the future.
        // ~O(n*m) where n = num ids, m = num entities
        List<Entity> entitiesResult = new ArrayList<Entity>();
        for (Integer id : ids) {
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getId() == id) {
                    entitiesResult.add(entities.get(i));
                    break;
                }
            }
        }

        return entitiesResult;
    }
}
