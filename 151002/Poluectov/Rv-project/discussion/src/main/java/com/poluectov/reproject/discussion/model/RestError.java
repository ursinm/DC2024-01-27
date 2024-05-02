package com.poluectov.reproject.discussion.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class RestError {

    HttpStatus status;
    String message;
}
