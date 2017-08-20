package com.m6code.jevloper;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 20/8/2017.
 * Helper methods related to requesting and receiving User data from Github
 */

public final class QueryUtils {
    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    private QueryUtils() {

    }

    /**
     * Query Github dataset and return a {@link User} object to represent a single user
     */
    public static List<User> fetchUserData(String requestURL) {
        // Create URL object
        URL url = createURL(requestURL);

        // Perform Http request to the URL and receive a JSON response back
        String JSONResponse = null;

        try {
            JSONResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making http request ", e);
        }

        // Extract relevant fields from the JSON response and create an {@link User} object
        List<User> users = extractUsers(JSONResponse);

        // Return the {@link User}
        return users;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL; return a string as the response
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String JSONResponse = "";

        // Check if URL is null, and return early
        if (url == null) {
            return JSONResponse;
        }

        // Make the connection if url in not null
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000 /*in milliseconds*/);
            urlConnection.setConnectTimeout(30000 /*in milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Check if the request was successful i.e response code 200,
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error reponse code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the user JSON results ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JSONResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link User} objects that has been built
     * up from parsing a JSON response
     */

    public static ArrayList<User> extractUsers(String userJSON) {
        if (TextUtils.isEmpty(userJSON)) {
            return null;
        }

        // Create an empty ArrayList that users are added to
        ArrayList<User> users = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of User objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(userJSON);
            JSONArray userArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < userArray.length(); i++) {
                JSONObject currentUser = userArray.getJSONObject(i);

                // extract the username from the key login
                String login = currentUser.getString("login");

                // extract the user profile url from the key
                String profileURL = currentUser.getString("html_url");

                User user = new User(login, profileURL);
                users.add(user);
            }
        }catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return users;

    }
}
