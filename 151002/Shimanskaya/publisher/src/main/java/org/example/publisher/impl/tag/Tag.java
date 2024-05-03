package org.example.publisher.impl.tag;

import org.example.publisher.impl.issue.Issue;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "tbl_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    BigInteger id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "tbl_m2m_issue_tag",
            joinColumns = @JoinColumn(name = "tg_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    private List<Issue> issues;
}
