package by.bsuir.publicator.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "tbl_editor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger ed_id;

    @Column(name = "ed_login", unique = true)
    private String ed_login;

    @Column(name = "ed_password")
    private String ed_password;

    @Column(name = "ed_firstname")
    private String ed_firstname;

    @Column(name = "ed_lastname")
    private String ed_lastname;
}
