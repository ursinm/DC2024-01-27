package by.bsuir.dc.rest_basics.entities;

import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Story extends AbstractEntity {

    private Long authorId;

    private String title;

    private String content;

    private Date created;

    private Date modified;

}
