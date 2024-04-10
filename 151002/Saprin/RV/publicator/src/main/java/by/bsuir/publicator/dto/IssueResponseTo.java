package by.bsuir.publicator.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
public class IssueResponseTo {
    private BigInteger id;
    private BigInteger editorId;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    public IssueResponseTo(BigInteger id, BigInteger editorId, String title, String content, Date created, Date modified) {
        this.id = id;
        this.editorId = editorId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}
