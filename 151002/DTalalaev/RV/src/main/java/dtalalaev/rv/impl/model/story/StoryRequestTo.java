package dtalalaev.rv.impl.model.story;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Builder
public class StoryRequestTo {
    private BigInteger id;
    BigInteger creatorId;
    @Size(min = 2, max = 64, message = "Title must be between 2 and 64 characters")
    private String title;
    @Size(min = 4, max = 2048, message = "Content must be between 4 and 2048 characters")
    private String content;

    private Date created;

    private Date modified;

}
