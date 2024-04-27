package com.distributed_computing.rest.exception;

public class NoSuchTweet extends RuntimeException{
    public NoSuchTweet(String message) {
        super(message);
    }
}
