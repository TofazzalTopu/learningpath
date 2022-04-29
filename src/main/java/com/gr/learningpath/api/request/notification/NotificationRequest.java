package com.gr.learningpath.api.request.notification;

import lombok.Data;

@Data
public class NotificationRequest {

    private Long userId;
    private String message;

}

