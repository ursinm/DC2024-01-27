package com.distributed_computing.jpa.exception;

public class IncorrectValuesException extends RuntimeException{
    public IncorrectValuesException(String message){
        super(message);
    }
}