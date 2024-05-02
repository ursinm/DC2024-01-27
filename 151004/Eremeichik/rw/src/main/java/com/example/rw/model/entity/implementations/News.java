package com.example.rw.model.entity.implementations;

import com.example.rw.model.entity.interfaces.EntityModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_news")
public class News implements EntityModel<Long> {

    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    private String title;
    private String content;
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date updateDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    private List<Sticker> stickers;
    @Transient
    private List<Message> messages;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
