package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import edu.temple.cis3238.gravity.gravity.activity.MainMenuActivity;
import edu.temple.cis3238.gravity.gravity.activity.OptionsActivity;
import edu.temple.cis3238.gravity.gravity.R;

/**
 * Created by bcrawford on 3/15/15.
 */
public class PauseDialogFragment extends DialogFragment {

    private Button optionsButton;
    private Button mainMenuButton;
    private Button exitButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.pause_dialog_title);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pause, null);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.resume_button, null);

        // Grab a reference to the buttons defined in activity_play_game.xml
        optionsButton = (Button) dialogView.findViewById(R.id.options_button);
        mainMenuButton = (Button) dialogView.findViewById(R.id.main_menu_button);
        exitButton = (Button) dialogView.findViewById(R.id.exit_button);

        // Set up event handling for the buttons
        // Options button event handler
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intents are used to pass data and declare your intentions
                // when loading a new activity. The first parameter states the
                // activity we're in and the second declares the activity we
                // wish to launch.
                Intent optionsIntent = new Intent(getActivity(), OptionsActivity.class);
                startActivity(optionsIntent);
            }
        });
        // Main menu button event handler
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // By calling finish(), we can destroy this activity. This will
                // return us to the previous activity, which in this case will
                // be the main menu activity
                getActivity().finish();
            }
        });
        // Exit button event handling
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here, we will use an intent to return to the main menu activity
                // and have the main menu activity exit the application. We can set
                // an extra (key-value pair) in the intent to signal the main menu
                // activity to exit.
                Intent exitIntent = new Intent(getActivity(), MainMenuActivity.class);
                exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exitIntent.putExtra("EXIT", true);
                startActivity(exitIntent);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}

