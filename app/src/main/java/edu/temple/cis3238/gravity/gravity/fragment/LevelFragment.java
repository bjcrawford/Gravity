package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.ConcurrentLinkedQueue;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.controller.ControllerThread;
import edu.temple.cis3238.gravity.gravity.model.Level;
import edu.temple.cis3238.gravity.gravity.model.game_state.GameState;
import edu.temple.cis3238.gravity.gravity.view.GamePlaySurface;
import edu.temple.cis3238.gravity.gravity.event.GameEvent;
import edu.temple.cis3238.gravity.gravity.event.GameEventQueue;
import edu.temple.cis3238.gravity.gravity.gesture_detection.GestureListener;

/**
 * A reusable level fragment.
 *
 * @author Brett Crawford
 * @version 1.0d last modified 4/6/2015
 */
public class LevelFragment extends Fragment {

    /* Debug tag */
    private static final String TAG = "LevelFragment";

    /* The fragment's view */
    private View view;

    /* The ControllerThread for handling the main game loop */
    private ControllerThread controllerThread;

    /* The Gesture classes used for receiving user input */
    private View gestureView;
    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener gestureListener;

    /* The SurfaceView used for drawing the game to the screen */
    private GamePlaySurface gameSurfaceView;

    /* The level model object */
    private Level level;

    // Temporary placement for testing, This should be in the controller class
    private GameEventQueue eventQueue;

    /* The interface to communicate with the parent activity (PlayGameActivity) */
    private OnLevelFragmentInteractionListener listener;

    /**
     * The required public empty constructor
     */
    public LevelFragment() {
        eventQueue = new GameEventQueue();
    }

    /**
     * Returns a new instance of a level fragment
     *
     * @param level The level model object
     * @return A LevelFragment
     */
    public static LevelFragment newInstance(Level level) {
        LevelFragment lf = new LevelFragment();
        lf.setLevel(level);
        lf.getLevel().initLevel();

        return lf;
    }

    /**
     * Sets the level associated with this fragment
     *
     * @param level A level model object
     */
    private void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Returns the level associated with this fragment
     *
     * @return A level model object
     */
    public Level getLevel() {
        return this.level;
    }

    // Temporary placement for testing, This should be in the controller class
    public ConcurrentLinkedQueue<GameEvent> getEventQueue() {
        return eventQueue;
    }

/* ===================================== Lifecycle Methods ====================================== */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach() fired");
        try {
            listener = (OnLevelFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLevelFragmentInteractionListener");
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_level, container, false);

        // Set up the gesture view
        gestureView = view.findViewById(R.id.gesture_view);
        gestureView.setClickable(true);
        gestureView.setFocusable(true);

        // Temporary placement for testing level end fragment on win/loss and pause
        ((Button) view.findViewById(R.id.win_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameState gamestate = new GameState(null, null, null);
                gamestate.setGameWon(true);
                onLevelEnd(gamestate);
            }
        });
        ((Button) view.findViewById(R.id.lose_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameState gamestate = new GameState(null, null, null);
                gamestate.setGameWon(false);
                onLevelEnd(gamestate);
            }
        });
        ((Button) view.findViewById(R.id.pause_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerThread.setPause(true);
                new PauseDialogFragment().setController(controllerThread).show(getFragmentManager(), null);
            }
        });

        // Set up the surface view, it is instantiated by the xml layout, fragment_level.xml,
        // and we get a reference to it here.
        gameSurfaceView = (GamePlaySurface) view.findViewById(R.id.game_play_surfaceview);

        // Set up the controller thread with a reference to the surfaceview
        controllerThread = new ControllerThread(gameSurfaceView, level.getModel(), eventQueue);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() fired");

        // First create the GestureListener that will include all our callbacks.
        // Then create the GestureDetector, which takes that listener as an argument.
        gestureListener = new GestureListener(eventQueue);
        gestureDetector = new GestureDetector(getActivity(), gestureListener);

        // For the view where gestures will occur, create an onTouchListener that sends
        // all motion events to the gesture detector.  When the gesture detector
        // actually detects an event, it will use the callbacks created in the
        // SimpleOnGestureListener.
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });

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

        // This is where we start the thread by calling to the surfaceviews init method
        gameSurfaceView.init(level.getModel());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() fired");
        controllerThread.setRun(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() fired");
        controllerThread.setPause(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() fired");
        controllerThread.setRun(false);
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
    public interface OnLevelFragmentInteractionListener {
        public void OnLevelFragmentInteraction(GameState gamestate);
    }

    /**
     * This method will handle communication to the parent activity.
     *
     * @param gamestate
     */
    public void onLevelEnd(GameState gamestate) {
        if (listener != null) {
            listener.OnLevelFragmentInteraction(gamestate);
        }
    }
}

