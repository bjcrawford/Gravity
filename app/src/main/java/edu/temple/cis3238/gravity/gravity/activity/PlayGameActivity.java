package edu.temple.cis3238.gravity.gravity.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.fragment.LevelEndFragment;
import edu.temple.cis3238.gravity.gravity.model.Level;
import edu.temple.cis3238.gravity.gravity.model.Story;
import edu.temple.cis3238.gravity.gravity.fragment.LevelFragment;
import edu.temple.cis3238.gravity.gravity.fragment.LevelSelectFragment;
import edu.temple.cis3238.gravity.gravity.fragment.PauseDialogFragment;
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
    private static final String LEVEL_END_FRAG = "LevelEndFragment";

    public static final float STANDARD_WIDTH = 1000f;
    public static final int PIXELS_PER_PHYSICS_GRID = 10;

    private Fragment currentFrag;
    private Story selectedStory;
    private Level selectedLevel;


/* ===================================== Lifecycle Methods ====================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");
        setContentView(R.layout.activity_play_game);

        // I don't think we will use the action bar at all.
        getActionBar().hide();

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

        // We'll use the onResume method to handle the creation of the info regarding the selected
        // options that are displayed to the toast message. This way the info will be updated
        // when the user adjusts the options using the pause menu in the play game activity.

        /*
        // Grab a reference to the shared preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        // Build a string using the shared preference values
        String info = "Selected options:\n  CheckBox: ";
        if (sp.getBoolean(OptionsActivity.PREF_CHECKBOX_KEY, true))
            info = info.concat("checked");
        else
            info = info.concat("unchecked");
        info = info.concat("\n  EditText: ");
        info = info.concat(sp.getString(OptionsActivity.PREF_EDITTEXT_KEY, ""));
        info = info.concat("\n  List: ");
        info = info.concat(sp.getString(OptionsActivity.PREF_LIST_KEY, "Item 1"));

        Log.d(TAG, info);
        */
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
     * This listener method will receive calls from any StorySelectFragment
     * that is added to this activity.
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
     * This listener method will receive calls from any LevelSelectFragment
     * that is added to this activity.
     * @param level The level object selected.
     */
    @Override
    public void OnLevelSelectFragmentInteraction(Level level) {
        selectedLevel = level;
        currentFrag = LevelFragment.newInstance(level);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, currentFrag, LEVEL_FRAG_TAG)
                .addToBackStack(LEVEL_FRAG_TAG)
                .commit();
    }

    /**
     * This listener method will receive calls from any LevelFragment
     * that is added to this activity.
     * @param gamestate
     */
    @Override
    public void OnLevelFragmentInteraction(GameState gamestate) {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        currentFrag = LevelEndFragment.newInstance(gamestate);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, currentFrag, LEVEL_END_FRAG)
                .commit();
    }

    /**
     * This listener method will receive calls from any LevelEndFragment
     * that is added to this activity.
     * @param uri
     */
    @Override
    public void OnLevelEndFragmentInteraction(Uri uri) {

    }
}
