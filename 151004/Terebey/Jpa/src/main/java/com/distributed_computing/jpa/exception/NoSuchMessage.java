package com.distributed_computing.jpa.exception;

public class NoSuchMessage extends RuntimeException{
    public NoSuchMessage(String message) {
        super(message);
    }
}