package edu.temple.cis3238.gravity.gravity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import edu.temple.cis3238.gravity.gravity.activity.MainMenuActivity;
import edu.temple.cis3238.gravity.gravity.activity.OptionsActivity;
import edu.temple.cis3238.gravity.gravity.R;
import edu.temple.cis3238.gravity.gravity.controller.ControllerThread;

/**
 * A DialogFragment for the pause menu.
 *
 * @author Brett Crawford
 * @version 1.0a last modified 3/15/2015
 */
public class PauseDialogFragment extends DialogFragment {

    /* The options button associated with the DialogFragment's layout */
    private Button optionsButton;

    /* The main menu button associated with the DialogFragment's layout */
    private Button mainMenuButton;

    /* The exit button associated with the DialogFragment's layout */
    private Button exitButton;

    /* A reference to the controller to release upon dialog dismissal */
    private ControllerThread controllerThread;

    /* A setter for the controller that allows chaining on construction */
    public PauseDialogFragment setController(ControllerThread controllerThread) {
        this.controllerThread = controllerThread;

        return this;
    }

    /**
     * Called when the Dialog is created. Handles defining button event
     * handling and building of the Dialog
     * @param savedInstanceState The saved instance state
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the DialogFragment's view using the layout
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pause, null);

        // Associate the button objects with the buttons defined in the DialogFragment's layout
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

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.pause_dialog_title);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.resume_button, null);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        controllerThread.setPause(false);
        synchronized (controllerThread) {
            controllerThread.notifyAll();
        }
    }

}

