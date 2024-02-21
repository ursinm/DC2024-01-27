package by.bsuir.rv.dto;


import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.IdentifiedBean;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IssueResponseTo extends IdentifiedBean {
    private BigInteger editorId;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    public IssueResponseTo(BigInteger id, BigInteger editorId, String title, String content, Date created, Date modified) {
        super(id);
        this.editorId = editorId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}
