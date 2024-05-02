package com.luschickij.DC_lab.service.exceptions;

public class ResourceNotFoundException extends ResourceException{
    public ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}
