package by.denisova.jpa.util.entity;

import by.denisova.jpa.model.Comment;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class CommentTestBuilder implements TestBuilder<Comment> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "dhjksc";
    private Story story = StoryTestBuilder.story().build();

    @Override
    public Comment build() {
        Comment note = new Comment();

        note.setId(id);
        note.setContent(content);
        note.setStory(story);

        return note;
    }
}
