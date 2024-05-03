package org.education.exception;

public class NoSuchTweet extends RuntimeException{
    public NoSuchTweet(String message) {
        super(message);
    }
}
