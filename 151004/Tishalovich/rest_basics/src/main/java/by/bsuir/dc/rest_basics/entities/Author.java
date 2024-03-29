package by.bsuir.dc.rest_basics.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblAuthor")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Story> stories;
}
