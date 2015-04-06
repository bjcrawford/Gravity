package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.concurrent.ConcurrentLinkedQueue;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.Level;
import edu.temple.cis3238.gravity.gravity.view.GamePlaySurface;
import edu.temple.cis3238.gravity.gravity.event.GameEvent;
import edu.temple.cis3238.gravity.gravity.event.GameEventQueue;
import edu.temple.cis3238.gravity.gravity.gesture_detection.GestureListener;

/**
 * A reusable level fragment.
 *
 * @author Brett Crawford
 * @version 1.0c last modified 4/5/2015
 */
public class LevelFragment extends Fragment implements SurfaceHolder.Callback {

    /* Debug tag */
    private static final String TAG = "LevelFragment";

    /* The fragment's view */
    private View view;

    /* The GestureView used for receiving user input */
    private View gestureView;

    /* The SurfaceView used for drawing the game to the screen */
    private GamePlaySurface gameSurfaceView;

    /* An interface for communication with the holder of the surface */
    private SurfaceHolder gameSurfaceHolder;

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

        // Temporary placement for testing level end fragment on win/loss
        ((Button) view.findViewById(R.id.win_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLevelEnd(true);
            }
        });
        ((Button) view.findViewById(R.id.lose_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLevelEnd(false);
            }
        });

        //set up the Surface view
     //   gameSurfaceView = (GamePlaySurface) view.findViewById(R.id.game_play_surfaceview);
        gameSurfaceView = new GamePlaySurface(getActivity());
        gameSurfaceHolder = gameSurfaceView.getHolder();
        gameSurfaceHolder.addCallback(this);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.fragment_layout);
        gameSurfaceView.setLayoutParams(layout.getLayoutParams());
        layout.addView(gameSurfaceView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() fired");

        // First create the GestureListener that will include all our callbacks.
        // Then create the GestureDetector, which takes that listener as an argument.
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener(eventQueue);
        final GestureDetector gd = new GestureDetector(getActivity(), gestureListener);

        // For the view where gestures will occur, create an onTouchListener that sends
        // all motion events to the gesture detector.  When the gesture detector
        // actually detects an event, it will use the callbacks created in the
        // SimpleOnGestureListener.
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
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
    public interface OnLevelFragmentInteractionListener {
        public void OnLevelFragmentInteraction(boolean won);
    }

    /**
     * This method will handle communication to the parent activity.
     *
     * @param won
     */
    public void onLevelEnd(boolean won) {
        if (listener != null) {
            listener.OnLevelFragmentInteraction(won);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Log.d(TAG, "SurfaceView width: " + gameSurfaceView.getWidth());
        Log.d(TAG, "SurfaceView height: " + gameSurfaceView.getHeight());
        //run the gamePlay thread as soon as the surface is created
        //TEST THREAD DRAWING
        Thread thread = new Thread() {
            @Override
            public void run() {
                Canvas canvas = gameSurfaceHolder.lockCanvas();
                //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player0), 0, 0, null);
                //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player3), 200, 200, null);

                Log.d(TAG, "Canvas width: " + canvas.getWidth());
                Log.d(TAG, "Canvas height: " + canvas.getHeight());

                float sf = gameSurfaceView.getWidth() / 1400f;
                Log.d(TAG, "Scaling Factor: " + sf);

                canvas.drawBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.player0),
                        null,
                        new RectF(sf * 0, sf * 0, sf * 100, sf * 100),
                        null);

                canvas.drawBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.player3),
                        null,
                        new RectF(sf * 200, sf * 200, sf * 300, sf * 300),
                        null);

                Paint whitePaint = new Paint();
                whitePaint.setColor(0xFFFFFFFF);
                for (int x = 0; x < canvas.getWidth(); x += sf * 50) {
                    for (int y = 0; y < canvas.getHeight(); y += sf * 50) {
                        canvas.drawPoint(x, y, whitePaint);
                    }
                }

                gameSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        };
        thread.start();
        //END TESTING
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //We will not need this
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

