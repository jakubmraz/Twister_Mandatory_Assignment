package com.example.twistermandatoryassignment.webapi;

import com.example.twistermandatoryassignment.classes.Comment;
import com.example.twistermandatoryassignment.classes.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwisterService {
    @GET("messages")
    Call<List<Message>> getAllMessages();

    @GET("messages/{messageId}")
    Call<Message> getMessageById(@Path("messageId") int messageId);

    @GET("messages")
    Call<List<Message>> getMessagesByUser(@Query("user") String username);

    @POST("messages")
    Call<Message> saveMessageBody(@Body Message message);

    @DELETE("messages/{id}")
    Call<Message> deleteMessage(@Path("id") int id);

    @PUT("messages/{id}")
    Call<Message> updateMessage(@Path("id") int id, @Body Message message);

    @GET("messages/{id}/comments")
    Call<List<Comment>> getAllComments(@Path("id") int commentId);

    @POST("messages/{id}/comments")
    Call<Comment> saveCommentBody(@Path("id") int messageId, @Body Comment comment);

    @DELETE("messages/{messageId}/comments/{commentId}")
    Call<Comment> deleteComment(@Path("messageId") int messageId, @Path("commentId") int commentId);
}
