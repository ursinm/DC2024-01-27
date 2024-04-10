package org.education.bean.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMessageResponse {
    String message;
    int index;
    List<CommentResponseTo> comments = new ArrayList<>();
}
