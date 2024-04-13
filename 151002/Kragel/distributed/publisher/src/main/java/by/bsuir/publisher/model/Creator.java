package by.bsuir.publisher.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "tbl_creator")
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", nullable = false, unique = true, length = 64)
    private String login;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();

}
