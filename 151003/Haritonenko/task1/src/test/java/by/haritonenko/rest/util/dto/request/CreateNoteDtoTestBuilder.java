package by.haritonenko.rest.util.dto.request;

import by.haritonenko.rest.dto.request.CreateNoteDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class CreateNoteDtoTestBuilder implements TestBuilder<CreateNoteDto> {

    private Long storyId = 1L;
    private String content = "dhjksc";

    @Override
    public CreateNoteDto build() {
        CreateNoteDto note = new CreateNoteDto();

        note.setContent(content);
        note.setStoryId(storyId);

        return note;
    }
}
