package by.bsuir.restapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Entity<Long> {

    private Long id;
    private Long issueId;
    private String content;

}
