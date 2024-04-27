package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.CreateCommentDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "comment")
@With
public class CreateCommentDtoTestBuilder implements TestBuilder<CreateCommentDto> {

    private Long storyId = 1L;
    private String content = "dhjksc";

    @Override
    public CreateCommentDto build() {
        CreateCommentDto comment = new CreateCommentDto();

        comment.setContent(content);
        comment.setStoryId(storyId);

        return comment;
    }
}
