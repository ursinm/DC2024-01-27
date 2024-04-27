package com.distributed_computing.jpa.bean;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tbl_issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    Creator creator;

    @OneToMany(mappedBy = "issue")
    List<Message> comments;

    @ManyToMany(mappedBy = "issue")
    List<Message> labels;

    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
}
