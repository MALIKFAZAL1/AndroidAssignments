package com.example.androidassignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessageFragment extends Fragment {
    private int messageId;
    Bundle bundle;
    boolean isTablet = false;
    UpdateList updateList;

    public MessageFragment() {
        // Required empty public constructor
    }

    public MessageFragment(Bundle bundle) {
        this.bundle =bundle;
    }

    public MessageFragment(Bundle bundle, boolean isTablet,UpdateList updateList) {
        this.bundle = bundle;
        this.isTablet = isTablet;
        this.updateList = updateList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(view1 -> {
            // Retrieve the message ID from the bundle
            int messageIdToDelete = bundle.getInt("messageId");
            ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(requireContext());
            dbHelper.deleteMessage(messageIdToDelete);
            // Finish the activity
            if(!isTablet){
                requireActivity().finish();
            }
            else {
                requireActivity().onBackPressed();
                updateList.updateListView();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_details, container, false);

        // Add any additional initialization or event handling logic as needed
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("messageId")) {
            messageId = bundle.getInt("messageId");

            // Display message details using the messageId
            displayMessageDetails(view);
        }

        return view;
    }
    private void displayMessageDetails(View view) {

        TextView messageIdTextView = view.findViewById(R.id.idTextView);
        TextView messageContentTextView = view.findViewById(R.id.messageTextView);

        // Example: Assume you have a ChatDatabaseHelper to query the details
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(requireContext());
       MessageDetails messageDetails = dbHelper.getMessageDetails(messageId);

        // Update TextViews with message details
        messageIdTextView.setText(String.valueOf(messageDetails.getId()));
        messageContentTextView.setText(messageDetails.getContent());
    }

    public interface UpdateList{
        void updateListView();
    }
}

