package com.distributed_computing.jpa.exception;

public class NoSuchTweet extends RuntimeException{
    public NoSuchTweet(String message) {
        super(message);
    }
}
