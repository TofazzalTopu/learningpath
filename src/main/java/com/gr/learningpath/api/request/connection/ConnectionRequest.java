package com.gr.learningpath.api.request.connection;

import lombok.Data;

@Data
public class ConnectionRequest {

    private Long fromUserId;
    private Long toUserId;

}
