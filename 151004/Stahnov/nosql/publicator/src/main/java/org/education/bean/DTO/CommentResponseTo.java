package org.education.bean.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseTo {
    int id;
    int tweetId;
    String content;
}
