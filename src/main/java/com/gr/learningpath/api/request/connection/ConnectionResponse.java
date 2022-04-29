package com.gr.learningpath.api.request.connection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ConnectionResponse {

    private Long id;
    private String username;

}
