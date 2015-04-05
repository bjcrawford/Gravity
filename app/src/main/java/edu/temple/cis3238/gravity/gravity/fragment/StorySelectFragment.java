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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.model.ContentPack;
import edu.temple.cis3238.gravity.gravity.model.Story;
import edu.temple.cis3238.gravity.gravity.storylistitem.StoryListItemAdapter;

/**
 * A story selection fragment.
 *
 * @author Brett Crawford
 * @version 1.0b last modified 4/5/2015
 */
public class StorySelectFragment extends Fragment implements
        StoryListItemAdapter.OnItemClickListener {

    private static final String TAG = "StorySelectFragment";

    private View view;
    private RecyclerView storyRecyclerView;

    private List<ContentPack> contentPacks;
    private List<Story> stories;

    private OnStorySelectFragmentInteractionListener listener;

    public StorySelectFragment() {
        // Required empty public constructor
    }

/* ===================================== Lifecycle Methods ====================================== */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach() fired");
        try {
            listener = (OnStorySelectFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStorySelectFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");

        // Create the arraylist of content pack objects
        contentPacks = new ArrayList<ContentPack>();

        // Add all of the content pack files. For now we must add each content pack
        // file individually, but we can revisit this in the future.
        contentPacks.add(0, new ContentPack(getActivity(), getContentPackJSONFromRes(R.raw.contentpack_test)));

        // Create the arraylist of story objects
        stories = new ArrayList<Story>();

        // Retrieve all stories from all content packs
        for (int i = 0; i < contentPacks.size(); i++) {
            stories.addAll(contentPacks.get(i).getStories());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() fired");
        view = inflater.inflate(R.layout.fragment_story_select, container, false);

        storyRecyclerView = (RecyclerView) view.findViewById(R.id.story_recyclerview);
        storyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        storyRecyclerView.setAdapter(new StoryListItemAdapter(stories, this));

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

/* ======================================= General Methods ====================================== */

    /**
     * Returns a JSONObject constructed from the resource id given. The resource id
     * should refer to a raw resource file containing a JSON object in UTF-8. If an
     * exception occurs, null is returned.
     * @param resId The resource id.
     * @return A JSONObject on success, otherwise null.
     */
    public JSONObject getContentPackJSONFromRes(int resId) {

        JSONObject contentPackJSONObject;
        InputStream is = getActivity().getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String fullFile = "";
        String line;
        try {
            while ((line = br.readLine()) != null) {
                fullFile += line;
            }
            contentPackJSONObject = new JSONObject(fullFile).getJSONObject("contentpack");

            return contentPackJSONObject;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

/* =========================== Parent Activity Communication Methods ============================ */

    /**
     * This method will communication to the parent activity.
     * @param story The story object selected
     */
    public void onStorySelected(Story story) {
        if (listener != null) {
            listener.OnStorySelectFragmentInteraction(story);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnStorySelectFragmentInteractionListener {
        public void OnStorySelectFragmentInteraction(Story story);
    }

/* =========================== RecyclerView Communication Methods ============================ */

    @Override
    public void onClick(View view, int position) {
        Log.d(TAG, "Story selected: " + position);
        Log.d(TAG, "Story info: " + stories.get(position).toString());
        onStorySelected(stories.get(position));
    }
}
