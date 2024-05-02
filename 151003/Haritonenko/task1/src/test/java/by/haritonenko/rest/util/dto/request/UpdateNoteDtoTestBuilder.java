package by.haritonenko.rest.util.dto.request;

import by.haritonenko.rest.dto.request.UpdateNoteDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class UpdateNoteDtoTestBuilder implements TestBuilder<UpdateNoteDto> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "dhjkssff";

    @Override
    public UpdateNoteDto build() {
        UpdateNoteDto note = new UpdateNoteDto();

        note.setId(id);
        note.setContent(content);
        note.setStoryId(storyId);

        return note;
    }
}
