package com.distributed_computing.rest.exception;

public class NoSuchMessage extends RuntimeException{
    public NoSuchMessage(String message) {
        super(message);
    }
}