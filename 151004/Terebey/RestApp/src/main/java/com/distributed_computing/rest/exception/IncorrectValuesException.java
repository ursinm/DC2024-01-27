package com.distributed_computing.rest.exception;

public class IncorrectValuesException extends RuntimeException{
    public IncorrectValuesException(String message){
        super(message);
    }
}