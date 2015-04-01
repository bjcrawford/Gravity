package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.RelativeLayout;

import java.util.concurrent.ConcurrentLinkedQueue;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.View.GamePlaySurface;
import edu.temple.cis3238.gravity.gravity.event.GameEvent;
import edu.temple.cis3238.gravity.gravity.event.GameEventQueue;
import edu.temple.cis3238.gravity.gravity.gesture_detection.GestureListener;

/**
 * A reusable level fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/21/2015
 */
public class LevelFragment extends Fragment implements SurfaceHolder.Callback{

    private static final String TAG = "LevelFragment";

    private View view;
    private View gestureView;
    private GamePlaySurface gameSurfaceView;
    private SurfaceHolder gameSurfaceHolder;

    private GameEventQueue eventQueue;

    public LevelFragment() {
        eventQueue = new GameEventQueue();
    }

    public ConcurrentLinkedQueue<GameEvent> getEventQueue() {
        return eventQueue;
    }


    /* ========================== Lifecycle Methods ========================== */

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach() fired");
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
        view = inflater.inflate(R.layout.level_fragment, container, false);

        // Set up the gesture view
        gestureView = view.findViewById(R.id.gesture_view);
        gestureView.setClickable(true);
        gestureView.setFocusable(true);
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
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //run the gamePlay thread as soon as the surface is created
        //TEST THREAD DRAWING
        Thread thread = new Thread() {
            @Override
            public void run() {
                Canvas canvas = gameSurfaceHolder.lockCanvas();
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.planet0), matrix, );
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
