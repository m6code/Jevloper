package com.m6code.jevloper;

/**
 * Created by Benjamin on 19/8/2017.
 * A class that holds list of github users
 */

public class User {

    private String mUsername;
    private String mProfileURL;
    private String mProfileImage;
    private String mProfileJsonURL;

    public  User(String username, String profileURL, String profileImage, String profileJsonURL){

        mUsername = username;
        mProfileURL = profileURL;
        mProfileImage = profileImage;
        mProfileJsonURL = profileJsonURL;
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

    /**
     * Get the profile image url
     * @return the profile image URL
     */
    public String getProfileImage(){
        return mProfileImage;
    }

    /**
     * Get the profileJsonURL
     * @return profile JSON URL
     */
    public String getProfileJsonURL(){
        return mProfileJsonURL;
    }
}
