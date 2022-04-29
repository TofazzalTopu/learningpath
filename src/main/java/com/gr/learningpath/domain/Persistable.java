package com.gr.learningpath.domain;

import java.io.Serializable;

public interface Persistable extends Serializable {
    boolean isPersisted();
}

