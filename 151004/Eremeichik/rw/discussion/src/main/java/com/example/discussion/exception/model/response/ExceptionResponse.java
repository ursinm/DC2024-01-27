package com.example.discussion.exception.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timestamp;

    private String message;

}
