package com.distributed_computing.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Creator {
    int id;
    String login;
    String password;
    String firstname;
    String lastname;
}