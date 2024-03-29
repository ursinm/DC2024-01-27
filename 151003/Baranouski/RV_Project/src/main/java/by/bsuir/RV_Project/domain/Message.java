package by.bsuir.RV_Project.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_message")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Message extends BaseEntity {
    @ManyToOne
    private Story story;

    private String content;
}
