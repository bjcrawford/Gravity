package edu.temple.cis3238.gravity.gravity.dlc;

import org.json.JSONException;
import org.json.JSONObject;

import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;
import edu.temple.cis3238.gravity.gravity.model.graphics2d.Graphics2D;
import edu.temple.cis3238.gravity.gravity.model.physicd2d.Physics2D;

/**
 * A Level object for holding JSON information.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class Level {

    /*The name of the level.*/
    private String name;

    /*The thumbnail resource id of the level.*/
    private String thumb;

    /*The description of the level.*/
    private String description;

    /*The width of the level.*/
    private int width;

    /*The height of the level.*/
    private int height;

    /*The width of the player.*/
    private int playerWidth;

    /*The height of the player.*/
    private int playerHeight;

    /*The time based scoring criteria of the level.*/
    private String goldScoring;
    private String silverScoring;
    private String bronzeScoring;

    /*The physics entities of the level.*/
    private Physics2D physics2d;

    /*The graphics entities of the level.*/
    private Graphics2D graphics2d;

    /*The gamestate entities of the level.*/
    private GameState gamestate;

    /**
     * A constructor for this object. The properties are filled using
     * the given JSONObject.
     * @param levelJSONObject The Level JSONObject.
     */
    public Level(JSONObject levelJSONObject) {
        try {
            name = levelJSONObject.getString("name");
            thumb = levelJSONObject.getString("thumb");
            description = levelJSONObject.getString("description");
            width = levelJSONObject.getInt("width");
            height = levelJSONObject.getInt("height");
            playerWidth = levelJSONObject.getInt("player_width");
            playerHeight = levelJSONObject.getInt("player_height");

            JSONObject scoringJSONObject = levelJSONObject.getJSONObject("scoring");
            goldScoring = scoringJSONObject.getString("gold");
            silverScoring = scoringJSONObject.getString("silver");
            bronzeScoring = scoringJSONObject.getString("bronze");

            physics2d = new Physics2D(levelJSONObject.getJSONObject("physics"));

            graphics2d = new Graphics2D(levelJSONObject.getJSONObject("graphics"));

            gamestate = new GameState(levelJSONObject.getJSONObject("gamestate"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
