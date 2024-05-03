package com.luschickij.publisher.model;

import lombok.*;
import org.springframework.http.HttpStatus;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestError {

    HttpStatus status;
    String post;
}
