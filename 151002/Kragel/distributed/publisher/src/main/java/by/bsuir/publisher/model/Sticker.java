package by.bsuir.publisher.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_sticker")
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_sticker_tweets",
            joinColumns = @JoinColumn(name = "sticker_id"),
            inverseJoinColumns = @JoinColumn(name = "tweets_id"))
    private List<Tweet> tweets = new ArrayList<>();

}