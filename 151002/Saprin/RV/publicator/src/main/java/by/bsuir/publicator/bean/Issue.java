package by.bsuir.publicator.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "tbl_issue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iss_id")
    private BigInteger iss_id;

    @JoinColumn(name = "iss_editor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Editor iss_editor;

    @Column(name = "iss_title", unique = true)
    @Length(min = 2, max = 64)
    private String iss_title;

    @Column(name = "iss_content", nullable = false)
    private String iss_content;

    @Column(name = "iss_created")
    private Date iss_created;

    @Column(name = "iss_modified")
    private Date iss_modified;
}
