package com.example.androidassignments;

public class ChatModel {
    int id;
    String chatMessage;

    public ChatModel(int id, String chatMessage) {
        this.id = id;
        this.chatMessage = chatMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
