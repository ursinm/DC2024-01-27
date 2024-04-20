package by.denisova.jpa.util.dto.comment.request;

import by.denisova.jpa.dto.request.CreateCommentDto;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "note")
@With
public class CreateCommentDtoTestBuilder implements TestBuilder<CreateCommentDto> {

    private Long storyId = 1L;
    private String content = "createdComment";

    @Override
    public CreateCommentDto build() {
        CreateCommentDto note = new CreateCommentDto();

        note.setContent(content);
        note.setStoryId(storyId);

        return note;
    }
}
