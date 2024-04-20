package by.denisova.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_story", schema = "distcomp")
@Getter
@Setter
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "modified")
    private LocalDateTime modified;

    @OneToMany(mappedBy = "story")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_story_marker",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "marker_id")
    )
    private Set<Marker> markers = new HashSet<>();
}
