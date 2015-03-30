package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.net.Uri;
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
import edu.temple.cis3238.gravity.gravity.dlc.DLC;
import edu.temple.cis3238.gravity.gravity.dlc.Level;
import edu.temple.cis3238.gravity.gravity.dlc.Story;
import edu.temple.cis3238.gravity.gravity.levellistitem.LevelListItemAdapter;
import edu.temple.cis3238.gravity.gravity.storylistitem.StoryListItemAdapter;

/**
 * A level selection fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class LevelSelectFragment extends Fragment implements
        LevelListItemAdapter.OnItemClickListener {

    private static final String TAG = "LevelSelectFragment";

    private View view;
    private RecyclerView levelRecyclerView;

    private List<Level> levels;

    private OnLevelSelectFragmentInteractionListener listener;

    public LevelSelectFragment() {
        levels = new ArrayList<Level>();
    }

    public static LevelSelectFragment instanceOf(Story story) {
        LevelSelectFragment lsf = new LevelSelectFragment();
        lsf.setLevels(story.getLevels());

        return lsf;
    }

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
     * This method will communication to the parent activity.
     * @param level The level object selected.
     */
    public void onLevelSelected(Level level) {
        if (listener != null) {
            listener.OnLevelSelectFragmentInteraction(level);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLevelSelectFragmentInteractionListener {
        public void OnLevelSelectFragmentInteraction(Level level);
    }

/* =========================== RecyclerView Communication Methods ============================ */

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "Level selected: " + position);
        Log.d(TAG, "Level info: " + levels.get(position).toString());
        onLevelSelected(levels.get(position));
    }
}
