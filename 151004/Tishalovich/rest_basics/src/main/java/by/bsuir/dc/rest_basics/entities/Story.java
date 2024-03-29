package by.bsuir.dc.rest_basics.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblStory")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(unique = true)
    private String title;

    private String content;

    private Date created;

    private Date modified;

    @OneToMany(mappedBy = "story")
    public List<Message> messages;
}
