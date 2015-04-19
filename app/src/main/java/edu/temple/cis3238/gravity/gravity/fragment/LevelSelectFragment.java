package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.Level;
import edu.temple.cis3238.gravity.gravity.model.Story;
import edu.temple.cis3238.gravity.gravity.levellistitem.LevelListItemAdapter;

/**
 * A level selection fragment.
 *
 * @author Brett Crawford
 * @version 1.0b last modified 4/5/2015
 */
public class LevelSelectFragment extends Fragment implements
        LevelListItemAdapter.OnItemClickListener {

    /* Debug tag */
    private static final String TAG = "LevelSelectFragment";

    /* The fragment's view */
    private View view;

    /* The RecyclerView that holds the list of levels */
    private RecyclerView levelRecyclerView;

    /* The levels to populate into the RecyclerView list */
    private List<Level> levels;

    /* The interface to communicate with the parent activity (PlayGameActivity) */
    private OnLevelSelectFragmentInteractionListener listener;

    /**
     * The required public empty constructor
     */
    public LevelSelectFragment() {
        levels = new ArrayList<Level>();
    }

    /**
     * Returns a new instance of a level select fragment
     *
     * @param story The story containing the levels to be used for selection
     * @return A LevelSelectFragment
     */
    public static LevelSelectFragment newInstance(Story story) {
        LevelSelectFragment lsf = new LevelSelectFragment();
        lsf.setLevels(story.getLevels());

        return lsf;
    }

    /**
     * Sets the levels associated with this fragment
     *
     * @param levels A list of Level objects
     */
    private void setLevels(List<Level> levels) {
        this.levels = levels;
    }

/* ===================================== Lifecycle Methods ====================================== */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach() fired");
        try {
            listener = (OnLevelSelectFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLevelSelectFragmentInteractionListener");
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
        view = inflater.inflate(R.layout.fragment_level_select, container, false);

        // Set up the RecyclerView
        levelRecyclerView = (RecyclerView) view.findViewById(R.id.level_recyclerview);
        levelRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        levelRecyclerView.setAdapter(new LevelListItemAdapter(levels, this));

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
     * This method will handle the communication of the selected level to the parent activity.
     *
     * @param selectedLevel The Level object selected.
     */
    public void onLevelSelected(Level selectedLevel) {
        if (listener != null) {
            listener.OnLevelSelectFragmentInteraction(selectedLevel);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLevelSelectFragmentInteractionListener {
        public void OnLevelSelectFragmentInteraction(Level selectedLevel);
    }

/* =========================== RecyclerView Communication Methods ============================ */

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "Level selected: " + position);
        Log.d(TAG, "Level info: " + levels.get(position).toString());
        onLevelSelected(levels.get(position));
    }
}
