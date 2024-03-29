package com.distributed_computing.jpa.bean.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatorResponseTo {
    int id;
    String login;
    String password;
    String firstname;
    String lastname;
}
