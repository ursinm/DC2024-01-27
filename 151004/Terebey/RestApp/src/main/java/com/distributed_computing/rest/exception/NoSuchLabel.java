package com.distributed_computing.rest.exception;

public class NoSuchLabel extends RuntimeException{
    public NoSuchLabel(String message) {
        super(message);
    }
}