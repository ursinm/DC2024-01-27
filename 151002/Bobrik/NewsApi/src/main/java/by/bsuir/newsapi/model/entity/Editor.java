package by.bsuir.newsapi.model.entity;

import by.bsuir.newsapi.model.AbstractEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@SuperBuilder
public class Editor extends AbstractEntity {
    private String login;

    private String password;

    private String firstName;

    private String lastName;
}
