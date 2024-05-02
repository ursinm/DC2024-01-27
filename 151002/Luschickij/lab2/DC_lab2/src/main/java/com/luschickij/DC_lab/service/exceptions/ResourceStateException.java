package com.luschickij.DC_lab.service.exceptions;

public class ResourceStateException extends ResourceException{
    public ResourceStateException(int code, String message) {
        super(code, message);
    }
}
