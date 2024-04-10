package by.bsuir.messageapp.model.entity;

import by.bsuir.messageapp.model.AEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Marker extends AEntity {
    private String name;
}
