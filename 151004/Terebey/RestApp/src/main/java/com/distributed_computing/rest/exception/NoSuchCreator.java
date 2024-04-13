package com.distributed_computing.rest.exception;

public class NoSuchCreator extends RuntimeException{
    public NoSuchCreator(String message) {
        super(message);
    }
}
