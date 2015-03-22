package edu.temple.cis3238.gravity.gravity.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import edu.temple.cis3238.gravity.gravity.R;


public class OptionsActivity extends PreferenceActivity {

    private static final String TAG = "OptionsActivity";

    // Some constants for referencing the shared preferences
    public static final String PREF_CHECKBOX_KEY = "pref_checkbox";
    public static final String PREF_EDITTEXT_KEY = "pref_edittext";
    public static final String PREF_LIST_KEY = "pref_list";

    // This is an inner class used to promote modularity. Different
    // fragments can easily be created to provide different options
    // depending on the situation (selecting options from the main
    // menu vs selecting options from inside the game ui).
    public static class OptionsFragment extends PreferenceFragment {

        private static final String TAG = "OptionsFragment";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Loads the layout of the preference screen.
            addPreferencesFromResource(R.xml.options);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() fired");

        // The options activity uses a fragment to display the preferences.
        // Preference choices are saved automatically.
        getFragmentManager().beginTransaction()
               .replace(android.R.id.content, new OptionsFragment())
               .commit();
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

}
