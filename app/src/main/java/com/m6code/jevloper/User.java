package com.m6code.jevloper;

/**
 * Created by Benjamin on 19/8/2017.
 * A class that holds list of github users
 */

public class User {

    private String mUsername;
    private String mProfileURL;

    public  User(String username, String profileURL){

        mUsername = username;
        mProfileURL = profileURL;
    }

    /**
     * Get the username of the user
     * @return the username
     */
    public String getUsername(){
        return mUsername;
    }

    /**
     * Get the profile url of the user
     * @return the profile URL
     */
    public String getProfileURL(){
        return mProfileURL;
    }
}
