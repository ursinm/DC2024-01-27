package com.distributed_computing.rest.exception;

public class AlreadyExists extends RuntimeException{
    public AlreadyExists(String message) {
        super(message);
    }
}