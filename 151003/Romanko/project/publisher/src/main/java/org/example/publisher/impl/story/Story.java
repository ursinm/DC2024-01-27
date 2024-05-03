package org.example.publisher.impl.story;

import org.example.publisher.impl.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "tbl_story")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    BigInteger id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "title", unique = true)
    @Length(min = 2, max = 64)
    String title;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "created")
    private Date created;
}
