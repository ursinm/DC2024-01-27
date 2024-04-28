package by.bsuir.restapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_markers")
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_marker_issues",
            joinColumns = @JoinColumn(name = "marker_id"),
            inverseJoinColumns = @JoinColumn(name = "issues_id"))
    private List<Issue> issues = new ArrayList<>();

}
