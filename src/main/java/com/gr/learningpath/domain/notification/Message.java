package com.gr.learningpath.domain.notification;

public class Message {

    private String message;

    public Message (String content) {
        this.message = content;
    }

    public String getContent() {
        return message;
    }

}
