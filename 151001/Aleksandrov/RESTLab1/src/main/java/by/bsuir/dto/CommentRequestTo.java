package by.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestTo {
    private Long id;
    private String content;
    private Long issueId;
}
