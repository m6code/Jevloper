package com.m6code.jevloper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileDetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = ProfileDetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        // Create a variable to receive intentExtras from the MainActivity
        final Intent receiver = getIntent();

        /* finds and set the username on the username TextView*/
        // Get the username from the MainActivity intentExtras
        String username = receiver.getStringExtra("username");
        // Find the username TextView from the activity_profile_details.xml layout
        TextView tvUsername = (TextView) findViewById(R.id.textView_username);
        // Set the textView
        tvUsername.setText(username);

        /* Find and set the user profile URL on the profile url TextView*/
        // Get the profile url from the MainActivity intentExtras
        String profileURL = receiver.getStringExtra("profileUrl");
        // Find the profile url TextView from the activity_profile_details.xml layout
        TextView tvProfileURL = (TextView) findViewById(R.id.textView_profile_url);
        // Set the Text from the on the TextView
        tvProfileURL.setText(profileURL);

        /*Set on click listener on the profile URL textView to launch the user profile*/
        tvProfileURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchProfile = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(receiver.getStringExtra("profileUrl")));
                startActivity(launchProfile);
            }
        });

        /* Find the share button and add onClickListener to share user profile*/
        // Find the button
        Button btShare = (Button) findViewById(R.id.button_share);
        // Set onClickListener on the button to launch Share Intent
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUser(receiver);
            }
        });

        /* Set on click listener for the FAB*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUser(receiver);
            }
        });

    }

    /**
     * Share the current user profile to available apps installed to receive text intent
     *
     * @param receiver is the intentExtras from the MainActivity.java class
     */
    private void shareUser(Intent receiver) {
        // Create an Intent to share user profile
        Intent shareUserProfile = new Intent(Intent.ACTION_SEND);
        shareUserProfile.setType("text/plain"); // Set the intent type
        // Put the intentExtras from the MainActivity
        shareUserProfile.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"
                + receiver.getStringExtra("username") + ", "
                + " " + receiver.getStringExtra("profileUrl"));

        startActivity(Intent.createChooser(shareUserProfile, "Share to"));
    }
}
