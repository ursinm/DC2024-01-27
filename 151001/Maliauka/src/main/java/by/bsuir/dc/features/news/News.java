package by.bsuir.dc.features.news;

import by.bsuir.dc.features.editor.Editor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    @Size(min = 2, max = 64)
    private String title;

    @Column(name = "content")
    @Size(min = 4, max = 2048)
    private String content;

    @Column(name = "created")
    private Instant created;

    @Column(name = "modified")
    private Instant modified;

    @JoinColumn(name = "editor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Editor editor;
}
