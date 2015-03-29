package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.dlc.DLC;

/**
 * A story selection fragment.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/29/2015
 */
public class StorySelectFragment extends Fragment {

    private static final String TAG = "StorySelectFragment";

    private View view;
    private TextView testText;

    private List<DLC> dlcs;

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

        // Create the arraylist of dlc objects
        dlcs = new ArrayList<DLC>();

        // Add all of the dlc files. For now we must add each dlc
        // file individually, but we can revisit this in the future.
        dlcs.add(0, new DLC(getDLCJSONFromRes(R.raw.dlc_test)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() fired");
        view = inflater.inflate(R.layout.fragment_story_select, container, false);

        testText = (TextView) view.findViewById(R.id.test_text);
        testText.setText(dlcs.get(0).toString());

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
    public JSONObject getDLCJSONFromRes(int resId) {

        JSONObject dlcJSONObject;
        InputStream is = getActivity().getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String fullFile = "";
        String line;
        try {
            while ((line = br.readLine()) != null) {
                fullFile += line;
            }
            dlcJSONObject = new JSONObject(fullFile).getJSONObject("dlc");

            return dlcJSONObject;
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
     * @param uri
     */
    public void onStorySelected(Uri uri) {
        if (listener != null) {
            listener.OnStorySelectFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnStorySelectFragmentInteractionListener {
        public void OnStorySelectFragmentInteraction(Uri uri);
    }
}
