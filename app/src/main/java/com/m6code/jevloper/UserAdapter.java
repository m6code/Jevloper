package com.m6code.jevloper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Benjamin on 19/8/2017.
 * * An ArrayAdapter used to populate the ListView
 * {@link UserAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link User} objects.
 */

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, ArrayList<User> users){
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link User} object located at this position in the list
        final User currentUser = getItem(position);

        // Find the username textView
        TextView tvUsername = (TextView)listItemView.findViewById(R.id.tv_username);
        // Set the Text for the current user
        tvUsername.setText(currentUser.getUsername());

        return listItemView;
    }
}
