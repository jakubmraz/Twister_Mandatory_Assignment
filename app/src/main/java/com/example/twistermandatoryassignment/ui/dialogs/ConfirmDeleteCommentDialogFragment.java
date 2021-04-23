package com.example.twistermandatoryassignment.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.twistermandatoryassignment.ui.TweetDetailsFragment;
import com.example.twistermandatoryassignment.webapi.WebRequests;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ConfirmDeleteCommentDialogFragment extends DialogFragment {
    private int messageId;
    private int commentToDeleteId;
    private TweetDetailsFragment tweetDetailsFragment;

    public ConfirmDeleteCommentDialogFragment(int messageId, int commentToDeleteId, TweetDetailsFragment tweetDetailsFragment) {
        this.messageId = messageId;
        this.commentToDeleteId = commentToDeleteId;
        this.tweetDetailsFragment = tweetDetailsFragment;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this comment?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WebRequests webRequests = new WebRequests();
                        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                            webRequests.deleteComment(messageId, commentToDeleteId);
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        try {
                            completableFuture.get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        webRequests.getAllComments(tweetDetailsFragment, messageId);
                        tweetDetailsFragment.MinusOneCommentNumber();
                    }
                })
                .setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
