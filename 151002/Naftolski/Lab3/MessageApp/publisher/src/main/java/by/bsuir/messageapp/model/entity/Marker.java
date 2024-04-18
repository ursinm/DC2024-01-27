package by.bsuir.messageapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_marker")
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, length = 32)
    private String name;

    @ManyToMany
    @JoinTable(name = "tbl_storymarker",
        joinColumns = @JoinColumn(name = "marker_id"),
        inverseJoinColumns = @JoinColumn(name = "story_id")
    )
    private List<Story> stories = new ArrayList<>();
}
