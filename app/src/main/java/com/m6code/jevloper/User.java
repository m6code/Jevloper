package com.m6code.jevloper;

/**
 * Created by Benjamin on 19/8/2017.
 * A class that holds list of github users
 */

public class User {

    private String mUsername;

    public  User(String username){

        mUsername = username;
    }

    /**
     * Get the username of the user
     * @return the username
     */
    public String getUsername(){
        return mUsername;
    }
}
