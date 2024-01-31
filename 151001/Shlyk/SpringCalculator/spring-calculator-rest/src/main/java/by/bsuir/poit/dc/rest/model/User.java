package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Size(min = 2, max = 64)
    @Column(name = "login",
	length = 64,
	nullable = false,
	unique = true)
    private String login;

    @Size(min = 8, max = 128)
    @Column(name = "password",
	length = 128,
	nullable = false)
    private String password;

    @Size(min = 2, max = 64)
    @Column(name = "first_name",
	length = 64,
	nullable = false)
    private String firstName;
    @Size(min = 2, max = 64)

    @Column(name = "last_name", length = 64, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<News> news = new ArrayList<>();
}
