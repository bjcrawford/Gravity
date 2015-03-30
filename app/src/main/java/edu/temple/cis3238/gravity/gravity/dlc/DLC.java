package edu.temple.cis3238.gravity.gravity.dlc;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A DLC object for holding JSON information.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class DLC {

    private static final String TAG = "DLC";

    /*The application context.*/
    private Context appContext;

    /*The dlc JSONObject.*/
    private JSONObject dlcJSONObject;

    /*The name of the DLC.*/
    private String name;

    /*The release date of the DLC.*/
    private String releaseDate;

    /*The author of the DLC.*/
    private String author;

    /*The notes of the DLC.*/
    private String notes;

    /*A list of the story objects in the DLC.*/
    private List<Story> stories;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param dlcJSONObject The DLC JSONObject.
     */
    public DLC(Context appContext, JSONObject dlcJSONObject) {
        this.appContext = appContext;
        this.dlcJSONObject = dlcJSONObject;
        try {
            name = dlcJSONObject.getString("name");
            Log.d(TAG, "Name: " + name);
            releaseDate = dlcJSONObject.getString("release_date");
            author = dlcJSONObject.getString("author");
            notes = dlcJSONObject.getString("notes");
            stories = new ArrayList<Story>();

            JSONArray jsonArrayStories = dlcJSONObject.getJSONArray("stories");
            for (int i = 0; i < jsonArrayStories.length(); i++) {
                stories.add(i, new Story(appContext, jsonArrayStories.getJSONObject(i)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the dlc JSONObject.
     * @return The dlc JSONObject.
     */
    public JSONObject getDlcJSONObject() {
        return dlcJSONObject;
    }

    /**
     * Returns the name of the DLC.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the release date of the DLC.
     * @return The release date.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Returns the author of the DLC.
     * @return The author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the notes of the DLC.
     * @return The notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns the list of story objects of the DLC.
     * @return A list of story objects.
     */
    public List<Story> getStories() {
        return stories;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Release Date: " + releaseDate +
                ", Author: " + author +
                ", Notes: " + notes +
                ", Number of stories: " + stories.size();
    }
}
