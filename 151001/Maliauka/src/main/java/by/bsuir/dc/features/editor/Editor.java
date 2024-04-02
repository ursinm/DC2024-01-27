package by.bsuir.dc.features.editor;

import by.bsuir.dc.features.news.News;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "tbl_editor")
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login")
    @Size(min = 2, max = 64)
    private String login;

    @Column(name = "password")
    @Size(min = 8, max = 128)
    private String password;

    @Column(name = "first_name")
    @Size(min = 2, max = 64)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 64)
    private String lastName;

    @OneToMany(mappedBy = "editor", fetch = FetchType.LAZY)
    private List<News> news = new ArrayList<>();
}
