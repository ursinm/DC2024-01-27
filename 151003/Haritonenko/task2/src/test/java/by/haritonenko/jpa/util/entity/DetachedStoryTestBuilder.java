package by.haritonenko.jpa.util.entity;

import by.haritonenko.jpa.model.Author;
import by.haritonenko.jpa.model.Story;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class DetachedStoryTestBuilder implements TestBuilder<Story> {

    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.of(2024, 1, 1, 4, 1);
    private LocalDateTime modified = LocalDateTime.of(2024, 2, 1, 4, 1);
    private Author author = AuthorTestBuilder.author().build();

    @Override
    public Story build() {
        Story story = new Story();

        story.setAuthor(author);
        story.setContent(content);
        story.setTitle(title);
        story.setCreated(created);
        story.setModified(modified);

        return story;
    }
}
