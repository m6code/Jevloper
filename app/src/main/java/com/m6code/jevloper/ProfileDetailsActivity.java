package com.m6code.jevloper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileDetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = ProfileDetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        // Create a variable to receive intentExtras from the MainActivity
        Intent receiver = getIntent();

        /* finds and set the username on the username TextView*/
        // Get the username from the MainActivity intentExtras
        String username = receiver.getStringExtra("username");
        // Find the username TextView from the activity_profile_details.xml layout
        TextView tvUsername = (TextView)findViewById(R.id.textView_username);
        // Set the textView
        tvUsername.setText(username);

        /* Find and set the user profile URL on the profile url TextView*/
        // Get the profile url from the MainActivity intentExtras
        String profileURL = receiver.getStringExtra("profileUrl");
        // Find the profile url TextView from the activity_profile_details.xml layout
        TextView tvProfileURL = (TextView)findViewById(R.id.textView_profile_url);
        // Set the Text from the on the TextView
        tvProfileURL.setText(profileURL);

    }
}
