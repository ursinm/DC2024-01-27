package com.example.rw.model.entity.implementations;


import com.example.rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_user")
public class User implements EntityModel<Long> {

    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    @OneToMany
    private List<News> news;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id){
        this.id = id;
    }
}
