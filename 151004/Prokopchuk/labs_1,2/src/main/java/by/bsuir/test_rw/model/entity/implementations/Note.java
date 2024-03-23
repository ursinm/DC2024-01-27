package by.bsuir.test_rw.model.entity.implementations;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tbl_note")
public class Note implements EntityModel<Long> {

    @GeneratedValue
    @Id
    private Long id;
    private String content;
    @ManyToOne
    private Issue issue;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }
}
