package com.gr.learningpath.util;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

public interface ResponseUtil {
    static <T> URI resourceUri(T resourceId) {
        Objects.requireNonNull(resourceId);
        return Optional.of(resourceId)
                .map(id -> fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri())
                .orElseThrow(IllegalStateException::new);
    }

    static <T> URI resourceUri() {
        return Optional.of(fromCurrentRequest().build().toUri()).orElseThrow(IllegalStateException::new);
    }
}
