package edu.temple.cis3238.gravity.gravity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.temple.cis3238.gravity.gravity.R;

/**
 * The main menu activity.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/15/2015
 */
public class MainMenuActivity extends Activity {

    // This string is user for debugging with the log. It is best practice to add
    // a tag like this to every class.
    private static final String TAG = "MainMenuActivity";

    private Button playGameButton;
    private Button optionsButton;
    private Button exitButton;

    // This is the first lifecycle method which is called when an activity is created.
    // There are more lifecycle methods listed below. You can find more about the
    // lifecycle methods at this link:
    // http://developer.android.com/training/basics/activity-lifecycle/starting.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This handles receiving the exit intent from the play game activity and exiting
        // the application. I'm not sure if this is the best place for it. Perhaps it
        // should be in onStart on onResume instead.
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        // Rather than using System.out.println(), you can use the Log class
        // to print to the debug console. ".d" specifies a debug statement. Other
        // statements exists as well: ".e" is error, ".i" is info, etc
        Log.d(TAG, "onCreate() fired");

        // The following line sets the view of the activity with the given xml layout
        setContentView(R.layout.activity_main_menu);

        // Now we associate the button variables with their buttons on the layout
        // The view IDs are defined in the activity_main_menu.xml file
        playGameButton = (Button) findViewById(R.id.play_game_button);
        optionsButton = (Button) findViewById(R.id.options_button);
        exitButton = (Button) findViewById(R.id.exit_button);

        // Set up event handling for the buttons
        // Play game button event handler
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Play game button clicked");
                // Intents are used to pass data and declare your intentions
                // when loading a new activity. The first parameter states the
                // activity we're in and the second declares the activity we
                // wish to launch.

                Intent playGameIntent = new Intent(MainMenuActivity.this, PlayGameActivity.class);
                startActivity(playGameIntent); // Launch the play game activity
            }
        });

        // Options button event handler
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Options button clicked");
                Intent optionsIntent = new Intent(MainMenuActivity.this, OptionsActivity.class);
                startActivity(optionsIntent);
            }
        });

        // Exit button event handler
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Exit button clicked");
                // The finish method destroys the current activity only. If it is the only activity
                // on the activity stack, the application will close. Otherwise, you would be
                // returned to the previous activity on the stack. In this case, the main menu
                // activity (the only activity on the stack) is destroyed and the application is
                // closed
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() fired");
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
