package org.example.publisher.impl.post;

import org.example.publisher.impl.issue.Issue;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @JoinColumn(name = "issue")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    @Column(name = "content")
    private String content;
}
