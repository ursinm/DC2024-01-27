package com.luschickij.discussion.repository.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String post) {
        super(post);
    }
}
