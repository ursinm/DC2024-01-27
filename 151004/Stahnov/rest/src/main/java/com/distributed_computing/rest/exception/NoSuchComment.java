package com.distributed_computing.rest.exception;

public class NoSuchComment extends RuntimeException{
    public NoSuchComment(String message) {
        super(message);
    }
}
