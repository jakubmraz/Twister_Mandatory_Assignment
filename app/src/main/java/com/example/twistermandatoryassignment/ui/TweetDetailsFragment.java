package com.example.twistermandatoryassignment.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.twistermandatoryassignment.R;
import com.example.twistermandatoryassignment.adapters.CommentAdapter;
import com.example.twistermandatoryassignment.classes.Comment;
import com.example.twistermandatoryassignment.classes.Message;
import com.example.twistermandatoryassignment.webapi.WebRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TweetDetailsFragment extends Fragment {

    public ArrayList<Comment> comments;
    public CommentAdapter commentAdapter;
    int messageId;
    String messageUser;
    String messageContent;
    int messageTotalComments;
    TextView viewTotalComments;
    View theView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet_details, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        theView = view;

        TextView viewUser = (TextView) view.findViewById(R.id.usernameDetails);
        TextView viewContent = (TextView) view.findViewById(R.id.tweetcontentDetails);
        viewTotalComments = (TextView) view.findViewById(R.id.numberofcommentsDetails);

        //For every time I want to pass this inside a method that'd pass something else as this
        TweetDetailsFragment theFragment = this;

        messageId = TweetDetailsFragmentArgs.fromBundle(getArguments()).getId();
        messageUser = TweetDetailsFragmentArgs.fromBundle(getArguments()).getUser();
        messageContent = TweetDetailsFragmentArgs.fromBundle(getArguments()).getContent();
        messageTotalComments = TweetDetailsFragmentArgs.fromBundle(getArguments()).getTotalComments();
        viewUser.setText(messageUser);
        viewContent.setText(messageContent);
        viewTotalComments.setText(messageTotalComments + "");

        RecyclerView rvComments = (RecyclerView) view.findViewById(R.id.rvComments);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvComments.addItemDecoration(itemDecoration);

        //Fake data
        comments = new ArrayList<Comment>();
        comments.add(new Comment(messageId, "lol ur tweets suck, consider kys", "BoggiJones"));
        comments.add(new Comment(messageId, "what do you mean 👏👏👏", "Yeast"));
        comments.add(new Comment(messageId, "lol", "FilthyMezla"));
        comments.add(new Comment(messageId, "he's spent", "Steel Seal"));
        comments.add(new Comment(messageId, "Understandable, Felix. You've had a rough day. Take all the rest you need", "HAdelivet123"));

        commentAdapter = new CommentAdapter(comments, currentUser, getChildFragmentManager(), this);
        rvComments.setAdapter(commentAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this.getContext()));

        WebRequests webRequests = new WebRequests();
        webRequests.getAllComments(this, messageId);

        Button sendCommentButton = view.findViewById(R.id.buttonSendComment);
        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("kek", "sadkek bro");
                try {
                    SendMessage(view.findViewById(R.id.editTextTypeComment), theFragment);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshDetails);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            webRequests.getAllComments(this, this.messageId);
            Log.d("kek", this.toString());
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    //Are methods supposed to be lowercase in Java? They're uppercase in C# and I like it that way
    private void SendMessage(EditText contentEditText, TweetDetailsFragment theFragment) throws ExecutionException, InterruptedException {
        Log.d("kek", contentEditText.toString());
        String commentContent = contentEditText.getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();

        Comment comment = new Comment(messageId, commentContent, email);

        Log.d("kek", comment.getContent() + comment.getUser());

        WebRequests webRequests = new WebRequests();
        //webRequests.uploadComment(comment);

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            webRequests.uploadComment(comment);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //Log.d("kek", theFragment.toString());
        //Log.d("kek", theFragment.messageId + "");

        completableFuture.get();
        this.messageTotalComments++;
        viewTotalComments.setText(messageTotalComments + "");
        webRequests.getAllComments(theFragment, theFragment.messageId);

        hideKeyboardFrom(theFragment.getContext(), theFragment.theView);
        contentEditText.setText("");
    }

    public void UpdateCommentsRv(ArrayList<Comment> newComments){
        this.comments.clear();
        this.comments.addAll(newComments);
        this.commentAdapter.notifyDataSetChanged();
    }

    public void MinusOneCommentNumber(){
        this.messageTotalComments--;
        viewTotalComments.setText(messageTotalComments + "");
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
