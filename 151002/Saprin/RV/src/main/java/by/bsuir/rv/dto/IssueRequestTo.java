package by.bsuir.rv.dto;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.IdentifiedBean;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IssueRequestTo extends IdentifiedBean {
    private BigInteger editorId;

    @Size(min = 2, max = 64)
    private String title;
    private String content;
    private Date created;
    private Date modified;

    public IssueRequestTo(BigInteger id, BigInteger editorId, String title, String content, Date created, Date modified) {
        super(id);
        this.editorId = editorId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}
