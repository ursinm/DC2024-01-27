package by.rusakovich.discussion.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NoteId implements Serializable {
    private String country;
    private Long newsId;
    private Long id;
}
