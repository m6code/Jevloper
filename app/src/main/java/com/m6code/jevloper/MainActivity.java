package com.m6code.jevloper;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<User>>{

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
    private TextView onErrorTextView;
    private ProgressBar onLoading;
    private static final int LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} from the activity_main layout
        ListView userListView = (ListView) findViewById(R.id.list);

        // Find the TextView
        onErrorTextView = (TextView) findViewById(R.id.textView_on_error);
        // Set the EmptyView for the listView
        userListView.setEmptyView(onErrorTextView);

        // Find the ProgressBar
        onLoading = (ProgressBar) findViewById(R.id.progressBar);

        // Create a new adapter that takes an empty list of users as input
        mAdapter = new UserAdapter(this, new ArrayList<User>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        userListView.setAdapter(mAdapter);

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

        // get reference to the connectivity manager to check network state
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get the current network details
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Check if network is available then fetch data
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            // Get reference to the loaderManager in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this);
        }else{
            // Display error, Hide progressBar
            onLoading.setVisibility(View.GONE);

            // Update the textView to show error message
            onErrorTextView.setText(R.string.no_internet);
        }
    }


    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        // Create new loader from the giver URL
        return new UserLoader(this, USER_DATA_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        // Hides the progressBar
        onLoading.setVisibility(View.GONE);

        // Set textView to show no user found if server returns no result
        onErrorTextView.setText(R.string.no_user_found);

        // Clears the adapter of previous user data
        mAdapter.clear();

        // If there is a valid list of {@link User}s, then add them to the adapter data
        // set, which will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
           mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        // Clear existing data
        mAdapter.clear();
    }
}
