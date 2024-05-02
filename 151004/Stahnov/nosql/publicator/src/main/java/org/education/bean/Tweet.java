package org.education.bean;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToMany(mappedBy = "tweets")
    List<Marker> markers;

    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
}
