package com.m6code.jevloper;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Benjamin on 26/8/2017.
 * Loads a list of users by using AsyncTask to
 * perform the network request to the given URL
 */

public class UserLoader extends AsyncTaskLoader {

    // Tag for log messages
    private static final String LOG_TAG = UserLoader.class.getName();

    // query URL
    private String mURL;

    /**
     * Construct a new {@link UserLoader}.
     * @param context of the activity
     * @param url to load the data from
     */
    public UserLoader(Context context, String url){
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is run on a background thread
     * @return null if no url is provided
     */
    @Override
    public List<User> loadInBackground() {
        if (mURL == null){
            return null;
        }

        // Perform network request, parse response and extract list of Users
        List<User> users = QueryUtils.fetchUserData(mURL);
        return users;
    }
}
