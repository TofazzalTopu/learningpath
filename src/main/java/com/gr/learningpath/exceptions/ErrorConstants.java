package com.gr.learningpath.exceptions;

import java.net.URI;

public interface ErrorConstants {
    String ERROR_KEY = "error";
    String LOCALIZED_MESSAGE = "serverResponse";
    String VIOLATION_KEY = "violations";
    String PROBLEM_BASE_URL = "https://learningpath.gr/problem";
    URI PROBLEM_BASE_URI = URI.create(PROBLEM_BASE_URL);
}
