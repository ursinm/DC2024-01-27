package com.distributed_computing.jpa.exception;

public class NoSuchCreator extends RuntimeException{
    public NoSuchCreator(String message) {
        super(message);
    }
}
