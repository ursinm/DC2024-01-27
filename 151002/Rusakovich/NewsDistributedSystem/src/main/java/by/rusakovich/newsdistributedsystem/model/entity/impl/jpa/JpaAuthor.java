package by.rusakovich.newsdistributedsystem.model.entity.impl.jpa;

import by.rusakovich.newsdistributedsystem.model.entity.impl.Author;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;


@Entity
@Table(name = "tbl_author")
public class JpaAuthor extends Author<Long> {
    @OneToMany(mappedBy = "author")
    private List<JpaNews> news;
}
