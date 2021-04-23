package com.example.twistermandatoryassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twistermandatoryassignment.R;
import com.example.twistermandatoryassignment.classes.Comment;
import com.example.twistermandatoryassignment.classes.Message;
import com.example.twistermandatoryassignment.ui.TweetDetailsFragment;
import com.example.twistermandatoryassignment.ui.dialogs.ConfirmDeleteCommentDialogFragment;
import com.example.twistermandatoryassignment.ui.dialogs.ConfirmDeleteTweetDialogFragment;
import com.example.twistermandatoryassignment.ui.dialogs.DeleteCommentErrorDialogFragment;
import com.example.twistermandatoryassignment.ui.dialogs.DeleteTweetErrorDialogFragment;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;
    private FirebaseUser currentUser;
    private FragmentManager fragmentManager;
    private TweetDetailsFragment tweetDetailsFragment;

    public CommentAdapter(List<Comment> comments, FirebaseUser currentUser,
                          FragmentManager fragmentManager, TweetDetailsFragment tweetDetailsFragment){
        this.comments = comments;
        this.currentUser = currentUser;
        this.fragmentManager = fragmentManager;
        this.tweetDetailsFragment = tweetDetailsFragment;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View commentView = inflater.inflate(R.layout.item_layout_comment, parent, false);

        // Return a new holder instance
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(commentView, new TweetAdapter.MyClickListener() {
            @Override
            public void onDelete(int p) {
                if(comments.get(p).getUser().equals(currentUser.getEmail())){
                    ConfirmDeleteCommentDialogFragment confirmDialog =
                            new ConfirmDeleteCommentDialogFragment(comments.get(p).getMessageId(), comments.get(p).getId(), tweetDetailsFragment);
                    confirmDialog.show(fragmentManager, "");
                }
                else {
                    DeleteCommentErrorDialogFragment errorDialog = new DeleteCommentErrorDialogFragment();
                    errorDialog.show(fragmentManager, "");
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Comment comment = comments.get(position);

        // Set item views based on your views and data model
        TextView usernameTextView = holder.usernameTextView;
        usernameTextView.setText(comment.getUser());
        TextView commentTextView = holder.commentTextView;
        commentTextView.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TweetAdapter.MyClickListener listener;

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView usernameTextView;
        public TextView commentTextView;
        public Button deleteCommentButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, TweetAdapter.MyClickListener listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            usernameTextView = (TextView) itemView.findViewById(R.id.usernameComment);
            commentTextView = (TextView) itemView.findViewById(R.id.commentContentComment);
            deleteCommentButton = (Button) itemView.findViewById(R.id.buttonCommentDelete);

            this.listener = listener;
            deleteCommentButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.buttonCommentDelete){
                listener.onDelete(this.getLayoutPosition());
            }
        }
    }
}
