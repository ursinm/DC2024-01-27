package by.haritonenko.rest.util.dto.request;

import by.haritonenko.rest.dto.request.UpdateStoryDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class UpdateStoryDtoTestBuilder implements TestBuilder<UpdateStoryDto> {

    private Long id = 1L;
    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public UpdateStoryDto build() {
        UpdateStoryDto story = new UpdateStoryDto();

        story.setId(id);
        story.setAuthorId(authorId);
        story.setContent(content);
        story.setTitle(title);

        return story;
    }
}