package by.rusakovich.newsdistributedsystem.model.entity.impl.jpa;

import by.rusakovich.newsdistributedsystem.model.entity.impl.News;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_news")
@Getter
@Setter
public class JpaNews extends News<Long> {

    @OneToMany(mappedBy = "news")
    private List<JpaNewsLabel> labels;

    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private JpaAuthor author;

    @OneToMany(mappedBy = "news")
    private List<JpaNote> notes;
}
