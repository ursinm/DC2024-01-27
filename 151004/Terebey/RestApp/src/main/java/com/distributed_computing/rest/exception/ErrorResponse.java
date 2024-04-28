package com.distributed_computing.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String errorMessage;
    int errorCode;
}