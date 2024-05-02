package by.haritonenko.rest.util.dto.request;

import by.haritonenko.rest.dto.request.CreateStoryDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class CreateStoryDtoTestBuilder implements TestBuilder<CreateStoryDto> {

    private Long authorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public CreateStoryDto build() {
        CreateStoryDto story = new CreateStoryDto();

        story.setAuthorId(authorId);
        story.setContent(content);
        story.setTitle(title);

        return story;
    }
}

