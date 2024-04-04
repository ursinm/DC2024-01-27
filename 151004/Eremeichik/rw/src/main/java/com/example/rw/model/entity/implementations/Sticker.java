package com.example.rw.model.entity.implementations;

import com.example.rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "tbl_sticker")
public class Sticker implements EntityModel<Long> {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    @ManyToMany
    private List<News> news;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
