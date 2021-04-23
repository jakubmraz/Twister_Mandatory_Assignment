package com.example.twistermandatoryassignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.twistermandatoryassignment.R;
import com.example.twistermandatoryassignment.adapters.TweetAdapter;
import com.example.twistermandatoryassignment.classes.Message;
import com.example.twistermandatoryassignment.webapi.WebRequests;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    public ArrayList<Message> messages = new ArrayList<Message>();
    private TweetAdapter tweetAdapter;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser currentUser = mAuth.getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvMessages = (RecyclerView) view.findViewById(R.id.rvProfileTweets);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvMessages.addItemDecoration(itemDecoration);

        messages = new ArrayList<Message>();

        tweetAdapter = new TweetAdapter(messages, getChildFragmentManager(), currentUser, this);
        rvMessages.setAdapter(tweetAdapter);
        rvMessages.setLayoutManager(new LinearLayoutManager(this.getContext()));

        WebRequests webRequests = new WebRequests();
        webRequests.getMessagesByUser(this, currentUser.getEmail());

        tweetAdapter.setOnItemClickListener(new TweetAdapter.OnItemClickListener<Message>() {
            @Override
            public void onItemClick(View view, int position, Message message) {
                int id = message.getId();
                String content = message.getContent();
                String user = message.getUser();
                int totalComments = message.getTotalComments();

                ProfileFragmentDirections.ActionNavProfileToNavTweet action =
                        ProfileFragmentDirections.actionNavProfileToNavTweet(content, user, totalComments, id);

                Navigation.findNavController(view).navigate(action);
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshProfile);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            webRequests.getMessagesByUser(this, currentUser.getEmail());
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        WebRequests webRequests = new WebRequests();
        webRequests.getMessagesByUser(this, currentUser.getEmail());
    }

    public void UpdateMessagesRv(ArrayList<Message> newMessages){
        this.messages.clear();
        this.messages.addAll(newMessages);
        this.tweetAdapter.notifyDataSetChanged();
    }

    public String GetEmail(){
        return currentUser.getEmail();
    }
}