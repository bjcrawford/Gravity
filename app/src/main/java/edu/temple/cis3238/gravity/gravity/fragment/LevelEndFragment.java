package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.Level;
import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;

/**
 * A level end fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 4/5/2015
 */
public class LevelEndFragment extends Fragment {

    /* Debug tag */
    private static final String TAG = "LevelEndFragment";

    /* An interface to communicate with the parent activity (PlayGameActivity) */
    public interface OnLevelEndFragmentInteractionListener {
        public void OnLevelEndFragmentLaunchLevel(Level level);
    }

    /* The interface to communicate with the parent activity (PlayGameActivity) */
    private OnLevelEndFragmentInteractionListener listener;

    /* The fragment's view */
    private View view;

    /* The state of the level end */
    private GameState gamestate;

    /* Buttons for navigating from this fragment */
    private Button retryButton;
    private Button nextLevelButton;
    private Button mainMenuButton;

    /* The level to use on retry button presses */
    private Level retryLevel;

    /* The level to use on next level button presses */
    private Level nextLevel;


    /**
     * The required public empty constructor
     */
    public LevelEndFragment() {
    }

    /**
     * Returns a new instance of a level end fragment. This will also need to take some sort
     * of level end state object that holds grade, score, time, etc...
     *
     * @return A LevelEndFragment.
     */
    public static LevelEndFragment newInstance(GameState gamestate, Level retryLevel, Level nextLevel) {
        LevelEndFragment lef = new LevelEndFragment();
        lef.setGameState(gamestate);
        lef.setRetryLevel(retryLevel);
        lef.setNextLevel(nextLevel);
        return lef;
    }

    private void setGameState(GameState gamestate) {
        this.gamestate = gamestate;
    }

    private void setRetryLevel(Level retryLevel) {
        this.retryLevel = retryLevel;
    }

    private void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }


/* ===================================== Lifecycle Methods ====================================== */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach() fired");
        try {
            listener = (OnLevelEndFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLevelEndFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() fired");

        if (gamestate.getGameWon()) { // Inflate the win layout
            view = inflater.inflate(R.layout.fragment_level_end_win, container, false);

            // TODO: Set level end info
            //((TextView) view.findViewById(R.id.grade)).setText("");
            ((TextView) view.findViewById(R.id.score)).setText(gamestate.getScore());
            //((TextView) view.findViewById(R.id.time)).setText("");

            nextLevelButton = (Button) view.findViewById(R.id.next_level_button);
            nextLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Next level button pressed");
                    Toast.makeText(getActivity(), "Not yet working...", Toast.LENGTH_LONG);
                }
            });

        }
        else { // Inflate the loss layout
            view = inflater.inflate(R.layout.fragment_level_end_loss, container, false);
        }

        retryButton = (Button) view.findViewById(R.id.retry_level_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Retry level button pressed");
                onLaunchLevelSelected(retryLevel);
            }
        });

        mainMenuButton = (Button) view.findViewById(R.id.main_menu_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Main menu button pressed");
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() fired");

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored() fired");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() fired");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() fired");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() fired");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() fired");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() fired");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() fired");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() fired");
        listener = null;
    }


/* =========================== Parent Activity Communication Methods ============================ */


    /**
     * Calls to the parent activity (PlayGameActivity) to request a level
     * to be launched.
     *
     * @param levelToLaunch The level to launch
     */
    public void onLaunchLevelSelected(Level levelToLaunch) {
        if (listener != null) {
            listener.OnLevelEndFragmentLaunchLevel(levelToLaunch);
        }
    }
}
