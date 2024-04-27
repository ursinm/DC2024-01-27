package com.distributed_computing.rest.exception;

public class NoSuchIssue extends RuntimeException{
    public NoSuchIssue(String message) {
        super(message);
    }
}