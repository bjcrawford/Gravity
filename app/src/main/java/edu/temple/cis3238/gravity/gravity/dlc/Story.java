package edu.temple.cis3238.gravity.gravity.dlc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A Story object for holding JSON information.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class Story {

    /*The name of the story.*/
    private String name;

    /*The thumbnail resource id of the story.*/
    private String thumb;

    /*The description of the story.*/
    private String description;

    /*A list of the level objects of the story.*/
    private List<Level> levels;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param storyJSONObject The Story JSONObject.
     */
    public Story(JSONObject storyJSONObject) {
        try {
            name = storyJSONObject.getString("name");
            thumb = storyJSONObject.getString("thumb");
            description = storyJSONObject.getString("description");
            levels = new ArrayList<Level>();

            JSONArray jsonArrayLevels = storyJSONObject.getJSONArray("levels");
            for (int i = 0; i < jsonArrayLevels.length(); i++) {
                levels.add(i, new Level(jsonArrayLevels.getJSONObject(i)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the story.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the thumbnail resource id of the story.
     * @return The thumbnail resource id.
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * Returns the description of the story.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a list of the level objects of the story.
     * @return A list of level objects.
     */
    public List<Level> getLevels() {
        return levels;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Thumb: " + thumb +
                ", Description: " + description +
                ", Number of levels: " + levels.size();
    }
}
