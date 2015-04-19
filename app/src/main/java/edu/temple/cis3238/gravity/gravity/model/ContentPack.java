package edu.temple.cis3238.gravity.gravity.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A content pack object for holding JSON information.
 *
 * @author Brett Crawford
 * @version 1.0b last modified 4/5/2015
 */
public class ContentPack {

    private static final String TAG = "ContentPack";

    /*The application context.*/
    private Context appContext;

    /*The content pack JSONObject.*/
    private JSONObject contentPackJSONObject;

    /*The name of the content pack.*/
    private String name;

    /*The release date of the content pack.*/
    private String releaseDate;

    /*The author of the content pack.*/
    private String author;

    /*The notes of the content pack.*/
    private String notes;

    /*A list of the story objects in the content pack.*/
    private List<Story> stories;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param contentPackJSONObject The content pack JSONObject.
     */
    public ContentPack(Context appContext, JSONObject contentPackJSONObject) {
        this.appContext = appContext;
        this.contentPackJSONObject = contentPackJSONObject;
        try {
            name = contentPackJSONObject.getString("name");
            releaseDate = contentPackJSONObject.getString("release_date");
            author = contentPackJSONObject.getString("author");
            notes = contentPackJSONObject.getString("notes");
            stories = new ArrayList<Story>();

            JSONArray storyJSONArray = contentPackJSONObject.getJSONArray("stories");
            for (int i = 0; i < storyJSONArray.length(); i++) {
                stories.add(i, new Story(appContext, storyJSONArray.getJSONObject(i)));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the content pack JSONObject.
     * @return The content pack JSONObject.
     */
    public JSONObject getContentPackJSONObject() {
        return contentPackJSONObject;
    }

    /**
     * Returns the name of the content pack.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the release date of the content pack.
     * @return The release date.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Returns the author of the content pack.
     * @return The author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the notes of the content pack.
     * @return The notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns the list of story objects of the content pack.
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
