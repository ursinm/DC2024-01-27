package by.denisova.jpa.util.entity;

import by.denisova.jpa.model.Editor;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class StoryTestBuilder implements TestBuilder<Story> {

    private Long id = 1L;
    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.of(2024, 1, 1, 4, 1);
    private LocalDateTime modified = LocalDateTime.of(2024, 2, 1, 4, 1);
    private Editor editor = EditorTestBuilder.editor().build();

    @Override
    public Story build() {
        Story story = new Story();

        story.setId(id);
        story.setEditor(editor);
        story.setContent(content);
        story.setTitle(title);
        story.setCreated(created);
        story.setModified(modified);

        return story;
    }
}
