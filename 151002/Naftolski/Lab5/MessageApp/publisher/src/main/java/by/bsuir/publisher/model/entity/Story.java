package by.bsuir.publisher.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_story")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Column(name = "title", unique = true, length = 64)
    private String title;

    @Column(name = "content", length = 2048)
    private String content;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;
}
