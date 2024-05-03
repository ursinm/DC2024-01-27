package by.haritonenko.jpa.util.entity;

import by.haritonenko.jpa.model.Note;
import by.haritonenko.jpa.model.Story;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class NoteTestBuilder implements TestBuilder<Note> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "dhjksc";
    private Story story = StoryTestBuilder.story().build();

    @Override
    public Note build() {
        Note note = new Note();

        note.setId(id);
        note.setContent(content);
        note.setStory(story);

        return note;
    }
}
