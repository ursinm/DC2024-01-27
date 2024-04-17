package by.rusakovich.publisher.model.entity.impl.jpa;

import by.rusakovich.publisher.model.entity.impl.Note;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_note")
@Getter
@Setter
public class JpaNote extends Note<Long> {
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private JpaNews news;
}
