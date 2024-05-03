package com.example.lab2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_story")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "editor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Editor editor;
    @Column(name = "title")
    @Size(min = 2, max = 64)
    private String title;
    @Column(name = "content")
    @Size(min = 4, max = 2048)
    private String content;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "modified")
    private Timestamp modified;


    @ManyToMany
    @JoinTable(
            name = "tbl_story_sticker",
            joinColumns = {@JoinColumn(name = "story_id")},
            inverseJoinColumns = {@JoinColumn(name = "sticker_id") }
    )
    private List<Sticker> stickers;

}

