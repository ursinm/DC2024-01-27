package org.example.publisher.impl.comment;

import org.example.publisher.impl.story.Story;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @JoinColumn(name = "story")
    @ManyToOne(fetch = FetchType.LAZY)
    private Story story;

    @Column(name = "content")
    private String content;
}
