package by.bsuir.dc.rest_basics.entities;

import by.bsuir.dc.rest_basics.entities.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Story extends AbstractEntity {

    private Long authorId;

    private String title;

    private String content;

    private Date created;

    private Date modified;

    public Story(Long id, Long authorId, String title, String content,
                 Date created, Date modified) {

        setId(id);
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;

    }

}
