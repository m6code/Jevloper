package com.m6code.jevloper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileDetailsMore extends AppCompatActivity {

    // create the log tag to log errors to console
    public static final String LOG_TAG = ProfileDetailsMore.class.getName();

    ImageView ivProfileImage;
    TextView tvUsername;
    TextView tvFullName;
    TextView tvBio;
    TextView tvProfileURL;
    TextView tvFollowing;
    TextView tvFollowers;
    TextView tvRepos;
    TextView tvLocation;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details_more);

        //Create intent to receive intent extras from the ProfileDetailsActivity.java
        Intent receiver = getIntent();

        // Receives string from intent
        String userDetailsURL = receiver.getStringExtra("jsonURL");

        // Find the user profile ImageView from activity_profile_details_more.xml
        ivProfileImage = (ImageView) findViewById(R.id.imageView_profile_image);

        // Find the username TextView  from activity_profile_details_more.xml
        tvUsername = (TextView) findViewById(R.id.textView_profile_username);

        // Find the user full name TextView from activity_profile_details_more.xml
        tvFullName = (TextView) findViewById(R.id.textView_profile_name_full);

        // Find the user bio TextView from activity_profile_details_more.xml
        tvBio = (TextView) findViewById(R.id.textView_user_profile_info);

        // Find the user profile URL TextView from activity_profile_details_more.xml
        tvProfileURL = (TextView) findViewById(R.id.textView_user_profile_url);

        // Find the user followers count TextView from activity_profile_details_more.xml
        tvFollowers = (TextView) findViewById(R.id.tv_followers_count);

        // Find the user following count TextView from activity_profile_details_more.xml
        tvFollowing = (TextView) findViewById(R.id.tv_following_count);

        // Find the user repos count TextView from activity_profile_details_more.xml
        tvRepos = (TextView) findViewById(R.id.tv_repo_count);

        // Find the user location TextView
        tvLocation = (TextView) findViewById(R.id.textView_user_location);

        // Find the Fab and set ClickListener on it
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        /** User Async task to run, fetch and pass JSON data in the background
         *  Users OkHttp Lib to carryout the task
         */
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(userDetailsURL);

    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... url) {
            Request.Builder builder = new Request.Builder();
            builder.url(url[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            try {
                JSONObject userDetailsObject = new JSONObject(data);

                // Extract the user profile image url
                String profileImageURL = userDetailsObject.getString("avatar_url");
                /*
                 * Use picasso lib to load user profile image into ImageView
                 */
                Picasso.with(ProfileDetailsMore.this)
                        .load(profileImageURL)
                        .into(ivProfileImage);

                // Extract the username of the profile from the login key
                final String username = userDetailsObject.getString("login");
                // Set the username on the username textView
                tvUsername.setText(username);

                // Extract the user full name
                String fullName = userDetailsObject.getString("name");
                tvFullName.setText(fullName); // Set the fullName on the TextView

                // Extract the user bio
                String userBio = userDetailsObject.getString("bio");
                if (userBio.equals("null")) {
                    tvBio.setVisibility(View.GONE);
                } else {
                    tvBio.setText(userBio);
                }


                // Extract the user profile URL
                final String profileURL = userDetailsObject.getString("html_url");
                tvProfileURL.setText(profileURL);

                // Extract the user followers count
                String followersCount = userDetailsObject.getString("followers");
                tvFollowers.setText(followersCount);

                // Extract the user following count
                String followingCount = userDetailsObject.getString("following");
                tvFollowing.setText(followingCount);

                // Extract the user repos count
                String repoCount = userDetailsObject.getString("public_repos");
                tvRepos.setText(repoCount);

                // Extract the user location
                String userLocation = userDetailsObject.getString("location");
                tvLocation.setText(userLocation);

                // Set Click listener on the fab
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create an Intent to share user profile
                        Intent shareUserProfile = new Intent(Intent.ACTION_SEND);
                        shareUserProfile.setType("text/plain"); // Set the intent type
                        // Put the intentExtras from the MainActivity
                        shareUserProfile.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"
                                + username + ", "
                                + " " + profileURL);
                        startActivity(Intent.createChooser(shareUserProfile, "Share to"));
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
