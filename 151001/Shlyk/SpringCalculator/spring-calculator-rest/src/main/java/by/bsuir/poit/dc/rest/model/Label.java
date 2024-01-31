package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Entity
@Table(name = "label")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Size(min = 2, max = 34)
    @Column(name = "name", length = 34, nullable = false)
    private String name;
}
