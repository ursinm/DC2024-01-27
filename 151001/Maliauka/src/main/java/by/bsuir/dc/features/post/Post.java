package by.bsuir.dc.features.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

@Getter
@Setter
@org.springframework.data.cassandra.core.mapping.Table("post")
@RequiredArgsConstructor
public class Post {
    @PrimaryKey
    private PostPrimaryKey postPrimaryKey;

    @Column(name = "content")
    @Size(min = 2, max = 2048)
    private String content;
}
