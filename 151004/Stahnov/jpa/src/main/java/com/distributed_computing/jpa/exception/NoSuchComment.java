package com.distributed_computing.jpa.exception;

public class NoSuchComment extends RuntimeException{
    public NoSuchComment(String message) {
        super(message);
    }
}
