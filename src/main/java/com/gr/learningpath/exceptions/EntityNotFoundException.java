package com.gr.learningpath.exceptions;

import com.gr.learningpath.domain.BaseEntity;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1338734201687057050L;
    private final Class<? extends BaseEntity> clazz;

    public EntityNotFoundException(Class<? extends BaseEntity> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getMessage() {
        return clazz.getSimpleName() + " not found";
    }
}
