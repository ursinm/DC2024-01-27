package by.haritonenko.jpa.util.dto.note.request;

import by.haritonenko.jpa.dto.request.UpdateNoteDto;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class UpdateNoteDtoTestBuilder implements TestBuilder<UpdateNoteDto> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "updatedContent";

    @Override
    public UpdateNoteDto build() {
        UpdateNoteDto note = new UpdateNoteDto();

        note.setId(id);
        note.setContent(content);
        note.setStoryId(storyId);

        return note;
    }
}
