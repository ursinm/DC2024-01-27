package by.rusakovich.publisher.model.entity.impl.jpa;

import by.rusakovich.publisher.model.entity.impl.Label;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "tbl_label")
public class JpaLabel extends Label <Long>{
    @OneToMany(mappedBy = "label")
    private List<JpaNewsLabel> news;
}
