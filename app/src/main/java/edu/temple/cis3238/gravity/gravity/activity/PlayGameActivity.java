package edu.temple.cis3238.gravity.gravity.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.fragment.LevelEndFragment;
import edu.temple.cis3238.gravity.gravity.model.Level;
import edu.temple.cis3238.gravity.gravity.model.Story;
import edu.temple.cis3238.gravity.gravity.fragment.LevelFragment;
import edu.temple.cis3238.gravity.gravity.fragment.LevelSelectFragment;
import edu.temple.cis3238.gravity.gravity.fragment.StorySelectFragment;
import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;

/**
 * The play game activity.
 *
 * @author Brett Crawford
 * @version 1.0b last modified 3/29/2015
 */
public class PlayGameActivity extends Activity implements
        StorySelectFragment.OnStorySelectFragmentInteractionListener,
        LevelSelectFragment.OnLevelSelectFragmentInteractionListener,
        LevelFragment.OnLevelFragmentInteractionListener,
        LevelEndFragment.OnLevelEndFragmentInteractionListener {

    private static final String TAG = "PlayGameActivity";

    private static final String STORY_SEL_FRAG_TAG = "StorySelectFragment";
    private static final String LEVEL_SEL_FRAG_TAG = "LevelSelectFragment";
    private static final String LEVEL_FRAG_TAG = "LevelFragment";
    private static final String LEVEL_END_FRAG_TAG = "LevelEndFragment";

    public static final float STANDARD_WIDTH = 1000f;
    public static final int PIXELS_PER_PHYSICS_GRID = 10;
    public static final float SWIPE_VEL_CONVERSION = 50000000f;
    public static final int ROCKET_BOOST_DURATION = 250;

    private Fragment currentFrag;
    private Story selectedStory;
    private Level selectedLevel;


/* ===================================== Lifecycle Methods ====================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");
        setContentView(R.layout.activity_play_game);

        // Create and add the story select fragment
        if (getFragmentManager().findFragmentByTag(STORY_SEL_FRAG_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new StorySelectFragment(), STORY_SEL_FRAG_TAG)
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() fired");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() fired");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() fired");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() fired");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() fired");
    }

    @Override
    public void onBackPressed() {
        if(currentFrag instanceof LevelFragment) {
            ((LevelFragment) currentFrag).launchPauseMenu();
        }
        else {
            super.onBackPressed();
        }
    }

/* ================================= Fragment Listener Methods ================================== */

    /**
     * This listener method will receive calls from the story select fragment
     * when a story has been selected.
     *
     * @param story The story object selected.
     */
    @Override
    public void OnStorySelectFragmentInteraction(Story story) {
        selectedStory = story;
        currentFrag = LevelSelectFragment.newInstance(story);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, currentFrag, LEVEL_SEL_FRAG_TAG)
                .addToBackStack(LEVEL_SEL_FRAG_TAG)
                .commit();
    }

    /**
     * This listener method will receive calls from the level select fragment
     * when a level has been selected.
     *
     * @param selectedLevel The level object selected.
     */
    @Override
    public void OnLevelSelectFragmentInteraction(Level selectedLevel) {
        this.selectedLevel = selectedLevel;
        currentFrag = LevelFragment.newInstance(selectedLevel);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, currentFrag, LEVEL_FRAG_TAG)
                .addToBackStack(LEVEL_FRAG_TAG)
                .commit();
    }

    /**
     * This listener method will receive calls from the level fragment when
     * it has finished.
     *
     * @param gamestate The game state at level end
     * @param currentLevel The level that was played
     */
    @Override
    public void OnLevelFragmentEnd(GameState gamestate, Level currentLevel, long time) {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        currentFrag = LevelEndFragment.newInstance(gamestate, currentLevel, null, time);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, currentFrag, LEVEL_END_FRAG_TAG)
                .commit();
    }

    /**
     * This listener method will receive calls from the level end fragment
     * on requests to launch a new level.
     *
     * @param levelToLaunch The level to play
     */
    @Override
    public void OnLevelEndFragmentLaunchLevel(Level levelToLaunch) {
        selectedLevel = levelToLaunch;
        currentFrag = LevelFragment.newInstance(levelToLaunch);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, currentFrag, LEVEL_FRAG_TAG)
                .addToBackStack(LEVEL_FRAG_TAG)
                .commit();
    }
}
