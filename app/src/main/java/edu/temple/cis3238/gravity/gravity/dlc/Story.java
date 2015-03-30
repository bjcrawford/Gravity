package edu.temple.cis3238.gravity.gravity.dlc;

import android.content.Context;
import android.util.Log;

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

    private static final String TAG = "Story";

    /*The application context.*/
    private Context appContext;

    /*The story JSONObject.*/
    private JSONObject storyJSONObject;

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
    public Story(Context appContext, JSONObject storyJSONObject) {
        this.appContext = appContext;
        this.storyJSONObject = storyJSONObject;
        try {
            name = storyJSONObject.getString("name");
            thumb = storyJSONObject.getString("thumb");
            description = storyJSONObject.getString("description");
            levels = new ArrayList<Level>();

            JSONArray jsonArrayLevels = storyJSONObject.getJSONArray("levels");
            for (int i = 0; i < jsonArrayLevels.length(); i++) {
                levels.add(i, new Level(appContext, jsonArrayLevels.getJSONObject(i)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the story JSONObject.
     * @return The story JSONObject.
     */
    public JSONObject getStoryJSONObject() {
        return storyJSONObject;
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
    public int getThumbResId() {
        String s1 = thumb.substring(thumb.lastIndexOf(".") + 1, thumb.length());
        String s2 = thumb.substring(thumb.indexOf(".") + 1, thumb.lastIndexOf("."));
        return appContext.getResources().getIdentifier(s1, s2, appContext.getPackageName());
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
