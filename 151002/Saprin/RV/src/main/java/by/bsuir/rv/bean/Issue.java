package by.bsuir.rv.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Issue extends IdentifiedBean {
    private BigInteger editorId;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    public Issue(BigInteger id, BigInteger editorId, String title, String content, Date created, Date modified) {
        super(id);
        this.editorId = editorId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}
