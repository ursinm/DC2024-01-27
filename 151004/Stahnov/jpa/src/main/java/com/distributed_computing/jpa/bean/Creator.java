package com.distributed_computing.jpa.bean;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "tbl_creator")
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany(mappedBy = "creator")
    List<Tweet> tweetList;

    String login;
    String password;
    String firstname;
    String lastname;
}
