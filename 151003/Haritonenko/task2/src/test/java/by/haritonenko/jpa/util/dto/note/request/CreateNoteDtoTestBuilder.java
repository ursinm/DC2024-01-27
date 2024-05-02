package by.haritonenko.jpa.util.dto.note.request;

import by.haritonenko.jpa.dto.request.CreateNoteDto;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class CreateNoteDtoTestBuilder implements TestBuilder<CreateNoteDto> {

    private Long storyId = 1L;
    private String content = "createdNote";

    @Override
    public CreateNoteDto build() {
        CreateNoteDto note = new CreateNoteDto();

        note.setContent(content);
        note.setStoryId(storyId);

        return note;
    }
}
