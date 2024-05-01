package by.denisova.rest.util.entity;

import by.denisova.rest.model.Comment;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "comment")
@With
public class DetachedCommentTestBuilder implements TestBuilder<Comment> {

    private Long storyId = 1L;
    private String content = "dhjksc";

    @Override
    public Comment build() {
        Comment comment = new Comment();

        comment.setContent(content);
        comment.setStoryId(storyId);

        return comment;
    }
}