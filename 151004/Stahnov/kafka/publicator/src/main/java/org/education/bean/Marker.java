package org.education.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "tbl_marker")
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    @ManyToMany
    @JoinTable(
            name = "TweetMarker",
            joinColumns = @JoinColumn(name = "tweetId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "markerId", referencedColumnName = "id")
    )
    List<Tweet> tweets;
}