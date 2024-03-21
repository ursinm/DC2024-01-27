package by.bsuir.rv.bean;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.math.BigInteger;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private BigInteger com_id;

    @JoinColumn(name = "com_issue")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue com_issue;

    @Column(name = "com_content")
    private String com_content;
}
