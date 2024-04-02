package com.distributed_computing.jpa.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String errorMessage;
    int errorCode;
}
