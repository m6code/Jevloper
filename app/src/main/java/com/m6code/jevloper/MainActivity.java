package com.m6code.jevloper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("user1"));
        users.add(new User("user2"));
        users.add(new User("user3"));
        users.add(new User("user4"));
        users.add(new User("user5"));
        users.add(new User("user6"));

        // Find a reference to the {@link ListView} from the activity_main layout
        ListView userListView = (ListView)findViewById(R.id.list);

        // Create a new adapter that takes an empty list of users as input
        UserAdapter adapter = new UserAdapter(this, users);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        userListView.setAdapter(adapter);
    }
}
