package org.example.publisher.impl.sticker;

import org.example.publisher.impl.story.Story;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "tbl_sticker")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    BigInteger id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "tbl_m2m_story_sticker",
            joinColumns = @JoinColumn(name = "sc_id"),
            inverseJoinColumns = @JoinColumn(name = "story_id")
    )
    private List<Story> storys;
}
