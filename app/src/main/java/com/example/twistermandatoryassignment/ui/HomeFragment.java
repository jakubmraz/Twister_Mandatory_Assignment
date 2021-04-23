package com.example.twistermandatoryassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.example.twistermandatoryassignment.webapi.ApiUtils;
import com.example.twistermandatoryassignment.webapi.TwisterService;
import com.example.twistermandatoryassignment.webapi.WebRequests;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public ArrayList<Message> messages = new ArrayList<Message>();
    private TweetAdapter tweetAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        RecyclerView rvMessages = (RecyclerView) view.findViewById(R.id.rvMessages);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvMessages.addItemDecoration(itemDecoration);

        //Fake data
        messages = new ArrayList<Message>();
        messages.add(new Message(1,"Bitch I'm bout that shit bitch, bitch I'm bout that","Bhad Bhabie", 15));
        messages.add(new Message(2,"Literally no one hit me up right now or you're fucking blocked, only some poeple know what's up.", "Goner", 1));
        messages.add(new Message(3, "I'm spent.", "Morfy", 0));



        tweetAdapter = new TweetAdapter(messages, getChildFragmentManager(), currentUser, this);
        rvMessages.setAdapter(tweetAdapter);
        rvMessages.setLayoutManager(new LinearLayoutManager(this.getContext()));

        WebRequests webRequests = new WebRequests();
        webRequests.getAllMessages(this);

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToWriteTweetActivity();
            }
        });

        tweetAdapter.setOnItemClickListener(new TweetAdapter.OnItemClickListener<Message>() {
            @Override
            public void onItemClick(View view, int position, Message message) {
                int id = message.getId();
                String content = message.getContent();
                String user = message.getUser();
                int totalComments = message.getTotalComments();

                HomeFragmentDirections.ActionNavHomeToNavTweet action =
                        HomeFragmentDirections.actionNavHomeToNavTweet(content, user, totalComments, id);

                Navigation.findNavController(view).navigate(action);
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshHome);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            webRequests.getAllMessages(this);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void switchToWriteTweetActivity() {
        Intent intentWriteTweetActivity = new Intent(this.getContext(), WriteTweetActivity.class);
        startActivity(intentWriteTweetActivity);
    }

    @Override
    public void onResume() {
        super.onResume();

        WebRequests webRequests = new WebRequests();
        webRequests.getAllMessages(this);
    }

    public void UpdateMessagesRv(ArrayList<Message> newMessages){
        this.messages.clear();
        this.messages.addAll(newMessages);
        this.tweetAdapter.notifyDataSetChanged();
    }
}