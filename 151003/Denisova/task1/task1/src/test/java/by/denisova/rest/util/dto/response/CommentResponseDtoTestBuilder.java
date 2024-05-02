package by.denisova.rest.util.dto.response;

import by.denisova.rest.dto.response.CommentResponseDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "comment")
@With
public class CommentResponseDtoTestBuilder implements TestBuilder<CommentResponseDto> {

    private Long id = 1L;
    private Long storyId = 1L;
    private String content = "dhjksc";

    @Override
    public CommentResponseDto build() {
        CommentResponseDto comment = new CommentResponseDto();

        comment.setId(id);
        comment.setContent(content);
        comment.setStoryId(storyId);

        return comment;
    }
}