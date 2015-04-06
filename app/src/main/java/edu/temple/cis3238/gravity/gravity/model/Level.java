package edu.temple.cis3238.gravity.gravity.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.Graphics2D;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.Physics2D;

/**
 * A Level object for holding JSON information.
 *
 * @author Brett Crawford
 * @version 1.0c last modified 4/6/2015
 */
public class Level {

    /*The application context.*/
    private Context appContext;

    /*The level JSONObject.*/
    private JSONObject levelJSONObject;

    /*The name of the level.*/
    private String name;

    /*The thumbnail resource id of the level.*/
    private String thumb;

    /*The description of the level.*/
    private String description;

    /*The time based scoring criteria of the level.*/
    private String goldScoring;
    private String silverScoring;
    private String bronzeScoring;

    /*The model of the level.*/
    private Model model;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     *
     * @param levelJSONObject The Level JSONObject.
     */
    public Level(Context appContext, JSONObject levelJSONObject) {
        this.appContext = appContext;
        this.levelJSONObject = levelJSONObject;
        try {
            name = levelJSONObject.getString("name");
            thumb = levelJSONObject.getString("thumb");
            description = levelJSONObject.getString("description");

            JSONObject scoringJSONObject = levelJSONObject.getJSONObject("scoring");
            goldScoring = scoringJSONObject.getString("gold");
            silverScoring = scoringJSONObject.getString("silver");
            bronzeScoring = scoringJSONObject.getString("bronze");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be used to instantiate model object and to prepare this
     * object for use.
     */
    public void initLevel() {

        /* Model class will be instantiated */
        try {
            JSONObject modelJSONObject = levelJSONObject.getJSONObject("model");
            model = new Model(modelJSONObject, 100, 100);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the level JSONObject.
     * @return The level JSONObject.
     */
    public JSONObject getLevelJSONObject() {
        return levelJSONObject;
    }

    /**
     * Returns the name of the level.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the thumbnail resource id of the level.
     * @return The thumbnail resource id.
     */
    public int getThumbResId() {
        String s1 = thumb.substring(thumb.lastIndexOf(".") + 1, thumb.length());
        String s2 = thumb.substring(thumb.indexOf(".") + 1, thumb.lastIndexOf("."));
        return appContext.getResources().getIdentifier(s1, s2, appContext.getPackageName());
    }

    /**
     * Returns the description of the level.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the gold score criteria of the level.
     * @return The gold score.
     */
    public String getGoldScoring() {
        return goldScoring;
    }

    /**
     * Returns the silver score criteria of the level.
     * @return The silver score.
     */
    public String getSilverScoring() {
        return silverScoring;
    }

    /**
     * Returns the bronze score criteria of the level.
     * @return The bronze score.
     */
    public String getBronzeScoring() {
        return bronzeScoring;
    }

    /**
     * Returns the Model object of the level.
     * @return The Model object.
     */
    public Model getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Thumb: " + thumb +
                ", Description: " + description +
                ", Gold Scoring: " + goldScoring +
                ", Silver Scoring: " + silverScoring +
                ", Bronze Scoring: " + bronzeScoring;
    }
}
