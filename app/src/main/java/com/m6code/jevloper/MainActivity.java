package com.m6code.jevloper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // create the log tag to log errors to console
    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL for user data from Github API
     * Uses the location (i.e location:lagos) to limit the query of users to a particular location
     * Uses the language (i.e language:java) to limit the query of users data to a specific language;
     * users that have java repos in this case
     */
    private static final String USER_DATA_REQUEST_URL =
            "https://api.github.com/search/users?q=location:lagos+language:java";

    /**
     * Adapter for the list of github users
     */
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} from the activity_main layout
        ListView userListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of users as input
        mAdapter = new UserAdapter(this, new ArrayList<User>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        userListView.setAdapter(mAdapter);

        UserAsyncTask userTask = new UserAsyncTask();
        userTask.execute(USER_DATA_REQUEST_URL);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get item position
                User user = mAdapter.getItem(i);
                // Create new intent to launchProfileDetails
                Intent launchProfileDetails = new Intent(MainActivity.this,
                        ProfileDetailsActivity.class);

                // pass the username to the intent
                launchProfileDetails.putExtra("username", user.getUsername());

                // pass the profile URL to the intent
                launchProfileDetails.putExtra("profileUrl", user.getProfileURL());

                // Pass the profile Image URL to the intent
                launchProfileDetails.putExtra("profileImageUrl", user.getProfileImage());

                // Pass the user profile JSON url to the intent
                launchProfileDetails.putExtra("jsonURL", user.getProfileJsonURL());

                startActivity(launchProfileDetails);
            }
        });
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread and then update
     * the User Interface with the list of users in the response.
     */
    private class UserAsyncTask extends AsyncTask<String, Void, List<User>> {
        /**
         * This method runs on a background thread and performs the network request
         * and returns a list of {@link User}s
         */
        @Override
        protected List<User> doInBackground(String... urls) {
            // Don't perform the request if no URLs is provided or if the 1st URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<User> result = QueryUtils.fetchUserData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background and receives an input return
         * value from the doInBackground() method
         */
        @Override
        protected void onPostExecute(List<User> data) {

            // Clears the adapter of previous user data
            mAdapter.clear();

            // If there is a valid list of {@link User}s, then add them to the adapter data
            // set, which will trigger the ListView to update.
            if (data !=null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }
}
