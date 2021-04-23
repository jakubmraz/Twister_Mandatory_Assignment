package com.example.twistermandatoryassignment.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.twistermandatoryassignment.ui.HomeFragment;
import com.example.twistermandatoryassignment.ui.ProfileFragment;
import com.example.twistermandatoryassignment.webapi.WebRequests;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ConfirmDeleteTweetDialogFragment extends DialogFragment {
    private int messageToDeleteId;
    private Fragment fragment;

    public ConfirmDeleteTweetDialogFragment(int id, Fragment fragment) {
        this.messageToDeleteId = id;
        this.fragment = fragment;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this message?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WebRequests webRequests = new WebRequests();
                        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                            webRequests.deleteMessage(messageToDeleteId);
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
                        if(fragment.getClass() == HomeFragment.class)
                            webRequests.getAllMessages((HomeFragment) fragment);
                        if(fragment.getClass() == ProfileFragment.class)
                            webRequests.getMessagesByUser((ProfileFragment) fragment, ((ProfileFragment) fragment).GetEmail());
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
