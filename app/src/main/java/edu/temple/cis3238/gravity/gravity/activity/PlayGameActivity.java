package edu.temple.cis3238.gravity.gravity.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.dlc.Level;
import edu.temple.cis3238.gravity.gravity.dlc.Story;
import edu.temple.cis3238.gravity.gravity.fragment.LevelFragment;
import edu.temple.cis3238.gravity.gravity.fragment.LevelSelectFragment;
import edu.temple.cis3238.gravity.gravity.fragment.PauseDialogFragment;
import edu.temple.cis3238.gravity.gravity.fragment.StorySelectFragment;

/**
 * The play game activity.
 *
 * @author Brett Crawford
 * @version 1.0b last modified 3/29/2015
 */
public class PlayGameActivity extends Activity implements
        StorySelectFragment.OnStorySelectFragmentInteractionListener,
        LevelSelectFragment.OnLevelSelectFragmentInteractionListener,
        LevelFragment.OnLevelFragmentInteractionListener {

    private static final String TAG = "PlayGameActivity";

    private static final String STORY_SEL_FRAG_TAG = "StorySelectFragment";
    private static final String LEVEL_SEL_FRAG_TAG = "LevelSelectFragment";
    private static final String LEVEL_FRAG_TAG = "LevelFragment";

    private Button pauseButton;

/* ===================================== Lifecycle Methods ====================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");
        setContentView(R.layout.activity_play_game);

        // I don't think we will use the action bar at all.
        getActionBar().hide();

        // Just testing pause functionality
        pauseButton = (Button) findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PauseDialogFragment().show(getFragmentManager(), null);
            }
        });

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

        // Display the string in the textview
        //gameInfo.setText(info); Change this to display a toast
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
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

/* ================================= Fragment Listener Methods ================================== */

    /**
     * This listener method will receive calls from any StorySelectFragment
     * that is added to this activity.
     * @param story The story object selected.
     */
    @Override
    public void OnStorySelectFragmentInteraction(Story story) {
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, LevelSelectFragment.instanceOf(story), LEVEL_SEL_FRAG_TAG)
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
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, LevelFragment.instanceOf(level), LEVEL_FRAG_TAG)
                .addToBackStack(LEVEL_FRAG_TAG)
                .commit();
    }

    /**
     * This listener method will receive calls from any LevelFragment
     * that is added to this activity.
     * @param uri
     */
    @Override
    public void OnLevelFragmentInteraction(Uri uri) {

    }
}
