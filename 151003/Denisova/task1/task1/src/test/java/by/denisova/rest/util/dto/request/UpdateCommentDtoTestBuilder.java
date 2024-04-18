package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.UpdateCommentDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "comment")
@With
public class UpdateCommentDtoTestBuilder implements TestBuilder<UpdateCommentDto> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "dhjkssff";

    @Override
    public UpdateCommentDto build() {
        UpdateCommentDto comment = new UpdateCommentDto();

        comment.setId(id);
        comment.setContent(content);
        comment.setStoryId(storyId);

        return comment;
    }
}
