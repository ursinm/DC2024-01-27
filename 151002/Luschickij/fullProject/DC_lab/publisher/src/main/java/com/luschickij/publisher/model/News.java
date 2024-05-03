package com.luschickij.publisher.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_news")
public class News extends IdentifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Creator creator;

    @Size(min = 2, max = 64)
    @Column(unique = true)
    private String title;

    @Size(min = 4, max = 2048)
    private String content;
    private Date created;
    private Date modified;

    @ManyToMany
    private List<Label> labels;
}
