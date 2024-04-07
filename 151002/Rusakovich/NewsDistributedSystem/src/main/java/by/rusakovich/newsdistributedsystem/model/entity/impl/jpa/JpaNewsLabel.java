package by.rusakovich.newsdistributedsystem.model.entity.impl.jpa;

import by.rusakovich.newsdistributedsystem.model.entity.IEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_news_label")
public class JpaNewsLabel implements IEntity<Long> {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long newId) {
        id = newId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private JpaNews news;

    @ManyToOne(fetch = FetchType.LAZY)
    private JpaLabel label;
}
