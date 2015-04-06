package edu.temple.cis3238.gravity.gravity.model.game_state;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.temple.cis3238.gravity.gravity.model.Point;
import edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece.Event;
import edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece.Objective;
import edu.temple.cis3238.gravity.gravity.model.game_state.gamepiece.Player;

/**
 *
 * @author Ian M. Speers
 * @version 1.0a last modified 3/21/2015
 */
public class GameState {

// Fields ------------------------------------------------------------------------------------------

    /**Holds a list of world events.*/
    private List<Event> events;

    /**Holds a list of objectives to be completed.*/
    private List<Objective> objectives;

    /**The player*/
    private Player player;

    /**Flag expressing the playability of the game.*/
    private boolean playable;

    /**Flag expressing the win/ lose state of the game.*/
    private boolean gameWon;

    /**Integer for tracking and rating the players progress.*/
    private int score;



// Constructors ------------------------------------------------------------------------------------


// General Public Functions ------------------------------------------------------------------------
    public GameState(List<Event> events, List<Objective> objectives, Player player) {
        this.events = events;
        this.objectives = objectives;
        this.playable = true;
        this.gameWon = false;
        this.score = 0;
    }

    public GameState(JSONObject selfAsJSON) {
        //TODO: json constructor
//        try {
//
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
        this.playable = true;
        this.gameWon = false;
        this.score = 0;
    }

// Private -----------------------------------------------------------------------------------------

    /**
     * Determine weather the euclidean distance between the two points is less than the given proximity.
     * @param p1 The first <x, y> point in question.
     * @param p2 The second <x, y> point in question.
     * @param proximity The maximum distance between the two points.
     * @return True if distance between the two points is less than proximity. False otherwise.
     */
    private boolean areProximal(Point p1, Point p2, int proximity) {
        // sqrt( a^2 + b^2 ) = c
        // return true if c <= proximity
        int a = p1.x - p2.x, b = p1.y - p2.y;
        int c = (a * a) + (b * b);
        if(c <= proximity) {
            return true;
        }else {
            return false;
        }
    }

// Protected ---------------------------------------------------------------------------------------

// Public ------------------------------------------------------------------------------------------

    public void updateGameState(Point playerPosition) {
        // If there are objectives remaining.
        if(this.objectives.size() > 0) {
            // If the Player is proximal to the next objective.
            if(this.areProximal(this.player.getPosition(), this.objectives.get(0).getPosition(),
                    this.player.getProximity() + this.objectives.get(0). getProximity())) {
                this.objectives.remove(0);
            }
        }

        //TODO: events


        if(this.objectives.size() == 0) {
            this.playable = false;
            this.gameWon = true;
        }
    }

// Getters and Setters -----------------------------------------------------------------------------

    public boolean getGameWon() {
        return this.gameWon;
    }

    public boolean getPlayable() {
        return this.playable;
    }

    public void setPlayable(boolean playable) {this.playable = playable;}
}
