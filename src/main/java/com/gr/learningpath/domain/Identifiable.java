package com.gr.learningpath.domain;

import java.io.Serializable;

public interface Identifiable<ID extends Serializable> {
    ID getId();
}
