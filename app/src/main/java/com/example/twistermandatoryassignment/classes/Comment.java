package com.example.twistermandatoryassignment.classes;

public class Comment {
    private int id;
    private int messageId;
    private String content;
    private String user;

    public Comment(int id, int messageId, String content, String user){
        this.id = id;
        this.messageId = messageId;
        this.content = content;
        this.user = user;
    }

    public Comment(int messageId, String content, String user){
        this.messageId = messageId;
        this.content = content;
        this.user = user;
    }

    public int getMessageId() {return this.messageId;}
    public int getId() {return  this.id;}
    public String getContent() {return this.content;}
    public String getUser() {return this.user;}
}
