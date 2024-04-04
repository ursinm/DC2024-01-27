package by.bsuir.rv.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private BigInteger st_id;

    @Column(name = "st_name")
    private String st_name;

    @ManyToMany
    @JoinTable(
            name = "tbl_m2m_issue_sticker",
            joinColumns = @JoinColumn(name = "st_id"),
            inverseJoinColumns = @JoinColumn(name = "iss_id")
    )
    private List<Issue> issues;
}
