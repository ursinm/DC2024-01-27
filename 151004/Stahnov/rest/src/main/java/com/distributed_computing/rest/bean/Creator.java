package com.distributed_computing.rest.bean;

import lombok.*;

@Data
@NoArgsConstructor
public class Creator {
    int id;
    String login;
    String password;
    String firstname;
    String lastname;
}
