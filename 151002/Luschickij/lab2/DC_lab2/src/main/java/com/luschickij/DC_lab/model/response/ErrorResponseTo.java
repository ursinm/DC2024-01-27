package com.luschickij.DC_lab.model.response;

public record ErrorResponseTo (
        int code,
        String message,
        String[] errorsMessages
){

}
