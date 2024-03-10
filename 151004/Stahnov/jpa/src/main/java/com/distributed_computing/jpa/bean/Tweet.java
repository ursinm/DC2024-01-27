package com.distributed_computing.jpa.bean;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tbl_tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    Creator creator;

    @OneToMany(mappedBy = "tweet")
    List<Comment> comments;

    @ManyToMany(mappedBy = "tweets")
    List<Marker> markers;

    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
}
