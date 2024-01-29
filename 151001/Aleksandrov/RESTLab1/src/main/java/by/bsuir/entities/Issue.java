package by.bsuir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long editorId;
    private String title;
    private String content;
    private Timestamp created;
    private Timestamp modified;
    @ManyToOne
    @JoinColumn(name = "editorId", insertable = false, updatable = false)
    private Editor editor;
    @ManyToMany
    @JoinTable(
            name = "issuelabel",
            joinColumns = @JoinColumn(name = "issueId"),
            inverseJoinColumns = @JoinColumn(name = "labelId")
    )
    private Set<Label> labels;
}
