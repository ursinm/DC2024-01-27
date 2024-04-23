package by.denisova.jpa.util.dto.comment.request;

import by.denisova.jpa.dto.request.UpdateCommentDto;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class UpdateCommentDtoTestBuilder implements TestBuilder<UpdateCommentDto> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "updatedContent";

    @Override
    public UpdateCommentDto build() {
        UpdateCommentDto note = new UpdateCommentDto();

        note.setId(id);
        note.setContent(content);
        note.setStoryId(storyId);

        return note;
    }
}
