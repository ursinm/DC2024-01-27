package org.education.bean.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.education.bean.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMessageResponse {
    String message;
    int index;
    List<Comment> comments = new ArrayList<>();
}
