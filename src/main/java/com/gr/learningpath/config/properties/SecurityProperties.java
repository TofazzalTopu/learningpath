package com.gr.learningpath.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityProperties {

    private final Jwt jwt = new Jwt();
    private final RememberMe rememberMe = new RememberMe();


    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private long tokenValidityInSeconds = 3600; // 1 hour
        private long tokenValidityInSecondsForRememberMe = 1296000; // 15 days
    }

    @Getter
    @Setter
    public static class RememberMe {
        private String key;
    }
}
