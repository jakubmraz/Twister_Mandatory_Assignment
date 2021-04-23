package com.example.twistermandatoryassignment.classes;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Message implements Serializable {
    @Expose
    private int id;
    @Expose
    private String content;
    @Expose
    private String user;
    @Expose
    private int totalComments;

    public Message(){
    }

    public Message(int id, String content, String user, int totalComments){
        this.id = id;
        this.content = content;
        this.user = user;
        this.totalComments = totalComments;
    }

    //For creating them
    public Message(String content, String user){
        this.content = content;
        this.user = user;
    }

    public int getId() {return this.id;}
    public void setId(int id) { this.id = id; }
    public String getContent() {return this.content;}
    public void setContent(String content) {this.content = content;}
    public String getUser() {return this.user;}
    public void setUser(String user) {this.user = user;}
    public int getTotalComments() {return this.totalComments;}
    public void setTotalComments(int totalComments) {this.totalComments = totalComments;}

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user='" + user + '\'' +
                ", totalComments=" + totalComments +
                '}';
    }
}
