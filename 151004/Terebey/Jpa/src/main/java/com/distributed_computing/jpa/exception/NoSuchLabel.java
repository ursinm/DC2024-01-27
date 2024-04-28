package com.distributed_computing.jpa.exception;

public class NoSuchLabel extends RuntimeException{
    public NoSuchLabel(String message) {
        super(message);
    }
}