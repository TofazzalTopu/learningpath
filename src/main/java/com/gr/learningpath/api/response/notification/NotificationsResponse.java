package com.gr.learningpath.api.response.notification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationsResponse {

    private Long fromUserId;
    private Long toUserId;
    private String message;

}
