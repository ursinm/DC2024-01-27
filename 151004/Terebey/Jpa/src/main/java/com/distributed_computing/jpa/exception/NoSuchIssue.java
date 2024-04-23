package com.distributed_computing.jpa.exception;

public class NoSuchIssue extends RuntimeException{
    public NoSuchIssue(String message) {
        super(message);
    }
}