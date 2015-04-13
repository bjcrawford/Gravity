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

import edu.temple.cis3238.gravity.gravity.R;
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

    /* The fragment's view */
    private View view;

    /* The state of the level end */
    private GameState gamestate;

    private Button retryButton;
    private Button nextLevelButton;
    private Button mainMenuButton;

    /* The interface to communicate with the parent activity (PlayGameActivity) */
    private OnLevelEndFragmentInteractionListener listener;

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
    public static LevelEndFragment newInstance(GameState gamestate) {
        LevelEndFragment lef = new LevelEndFragment();
        lef.setGameState(gamestate);
        return lef;
    }

    /**
     * Sets the state of the won flag
     *
     * @param gamestate
     */
    private void setGameState(GameState gamestate) {
        this.gamestate = gamestate;
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
            //((TextView) view.findViewById(R.id.score)).setText("");
            //((TextView) view.findViewById(R.id.time)).setText("");

            nextLevelButton = (Button) view.findViewById(R.id.next_level_button);
            nextLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Next level button pressed");
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
            }
        });

        mainMenuButton = (Button) view.findViewById(R.id.main_menu_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Main menu button pressed");
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLevelEndFragmentInteractionListener {
        public void OnLevelEndFragmentInteraction(Uri uri);
    }

    public void onSomeEvent(Uri uri) {
        if (listener != null) {
            listener.OnLevelEndFragmentInteraction(uri);
        }
    }

}
