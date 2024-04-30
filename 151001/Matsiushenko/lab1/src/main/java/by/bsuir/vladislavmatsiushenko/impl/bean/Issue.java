package by.bsuir.vladislavmatsiushenko.impl.bean;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Setter
@Getter
public class Issue {
    private long id;
    private long userId;
    private String title;
    private String content;
    private Date created;
    private Date modified;
}
