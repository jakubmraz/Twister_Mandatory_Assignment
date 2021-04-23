package com.example.twistermandatoryassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twistermandatoryassignment.R;
import com.example.twistermandatoryassignment.classes.Message;
import com.example.twistermandatoryassignment.ui.HomeFragment;
import com.example.twistermandatoryassignment.ui.dialogs.ConfirmDeleteTweetDialogFragment;
import com.example.twistermandatoryassignment.ui.dialogs.DeleteTweetErrorDialogFragment;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class TweetAdapter extends
        RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    public interface OnItemClickListener<Message> {
        void onItemClick(View view, int position, Message message);
    }

    public void setOnItemClickListener(OnItemClickListener<Message> itemClickListener){
        this.onItemClickListener = itemClickListener;
    }

    private List<Message> messages;
    private OnItemClickListener<Message> onItemClickListener;
    private FragmentManager fragmentManager;
    private FirebaseUser currentUser;
    private Fragment homeFragment;

    public TweetAdapter(List<Message> messages, FragmentManager childFragmentManager, FirebaseUser currentUser, Fragment fragment){
        this.messages = messages;
        this.fragmentManager = childFragmentManager;
        this.currentUser = currentUser;
        this.homeFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.item_layout_tweet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(tweetView, new MyClickListener() {
            @Override
            public void onDelete(int p){
                //Toast.makeText(context, messages.get(p).getUser() + "", Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, currentUser.getEmail() + "", Toast.LENGTH_SHORT).show();
                //boolean huh = messages.get(p).getUser().equals(currentUser.getEmail());
                //Toast.makeText(context,  huh + "", Toast.LENGTH_SHORT).show();
                if(messages.get(p).getUser().equals(currentUser.getEmail())){
                    ConfirmDeleteTweetDialogFragment confirmDialog = new ConfirmDeleteTweetDialogFragment(messages.get(p).getId(), homeFragment);
                    confirmDialog.show(fragmentManager, "");
                }
                else {
                    DeleteTweetErrorDialogFragment errorDialog = new DeleteTweetErrorDialogFragment();
                    errorDialog.show(fragmentManager, "");
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Message message = messages.get(position);

        // Set item views based on your views and data model
        TextView usernameTextView = holder.usernameTextView;
        usernameTextView.setText(message.getUser());
        TextView messageTextView = holder.messageTextView;
        messageTextView.setText(message.getContent());
        TextView totalCommentsTextView = holder.numberOfCommentsTextView;
        totalCommentsTextView.setText(message.getTotalComments() + "");
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView usernameTextView;
        public TextView messageTextView;
        public TextView numberOfCommentsTextView;
        public Button deleteTweetButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, MyClickListener listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            usernameTextView = (TextView) itemView.findViewById(R.id.username);
            messageTextView = (TextView) itemView.findViewById(R.id.tweetcontent);
            numberOfCommentsTextView = (TextView) itemView.findViewById(R.id.numberofcomments);
            deleteTweetButton = (Button) itemView.findViewById(R.id.buttonTweetDelete);

            this.listener = listener;

            itemView.setOnClickListener(this);
            deleteTweetButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if(view.getId() == R.id.buttonTweetDelete){
                listener.onDelete(this.getLayoutPosition());
            }
            else {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(itemView, position, messages.get(getAdapterPosition()));
                    }
                }
            }
        }
    }
    public interface MyClickListener{
        void onDelete(int p);
    }
}