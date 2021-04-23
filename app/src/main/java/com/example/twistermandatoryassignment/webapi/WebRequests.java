package com.example.twistermandatoryassignment.webapi;

import android.util.Log;

import com.example.twistermandatoryassignment.classes.Comment;
import com.example.twistermandatoryassignment.classes.Message;
import com.example.twistermandatoryassignment.ui.HomeFragment;
import com.example.twistermandatoryassignment.ui.ProfileFragment;
import com.example.twistermandatoryassignment.ui.TweetDetailsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebRequests {
    public void getAllMessages(HomeFragment homeFragment){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<List<Message>> getAllMessagesCall = twisterService.getAllMessages();

        getAllMessagesCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                Log.d("kek", response.raw().toString());
                if (response.isSuccessful()) {
                    List<Message> allMessages = response.body();
                    //Log.d("kek", allMessages.toString());
                    homeFragment.UpdateMessagesRv((ArrayList<Message>) allMessages);
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d("kek", message);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }

    public void getMessagesByUser(ProfileFragment profileFragment, String username){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<List<Message>> getAllMessagesCall = twisterService.getMessagesByUser(username);

        getAllMessagesCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                Log.d("kek", response.raw().toString());
                if (response.isSuccessful()) {
                    List<Message> allMessages = response.body();
                    //Log.d("kek", allMessages.toString());
                    profileFragment.UpdateMessagesRv((ArrayList<Message>) allMessages);
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d("kek", message);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }

    public void uploadMessage(Message message){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<Message> uploadMessageCall = twisterService.saveMessageBody(message);

        uploadMessageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d("kek", response.raw().toString());
                if (response.isSuccessful()) {
                    Message sentMessage = response.body();
                    //Log.d("kek", sentMessage.toString());
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d("kek", message);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }

    public void uploadComment(Comment comment){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<Comment> uploadCommentCall = twisterService.saveCommentBody(comment.getMessageId(), comment);

        uploadCommentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Log.d("kek", response.raw().toString());
                if (response.isSuccessful()) {
                    Comment sentComment = response.body();
                    //Log.d("kek", sentMessage.toString());
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d("kek", message);
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }

    public void getAllComments(TweetDetailsFragment detailsFragment, int messageId){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<List<Comment>> getAllCommentsCall = twisterService.getAllComments(messageId);

        getAllCommentsCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                Log.d("kek", response.raw().toString());
                if (response.isSuccessful()) {
                    List<Comment> allComments = response.body();
                    //Log.d("kek", allMessages.toString());
                    detailsFragment.UpdateCommentsRv((ArrayList<Comment>) allComments);
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d("kek", message);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }

    public void deleteMessage(int messageId){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<Message> deleteMessageCall = twisterService.deleteMessage(messageId);

        deleteMessageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d("kek", response.raw().toString());
                String message;
                if (response.isSuccessful()) {
                    message = "Success " + response.code() + " " + response.message();
                } else {
                    message = "Problem " + response.code() + " " + response.message();
                }
                Log.d("kek", message);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }

    public void deleteComment(int messageId, int commentId){
        TwisterService twisterService = ApiUtils.getTwisterService();
        Call<Comment> deleteCommentCall = twisterService.deleteComment(messageId, commentId);

        deleteCommentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Log.d("kek", response.raw().toString());
                String message;
                if (response.isSuccessful()) {
                    message = "Success " + response.code() + " " + response.message();
                } else {
                    message = "Problem " + response.code() + " " + response.message();
                }
                Log.d("kek", message);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("kek", t.getMessage());
            }
        });
    }
}
