package by.haritonenko.jpa.util.dto.story.request;

import by.haritonenko.jpa.dto.request.CreateStoryDto;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class CreateStoryDtoTestBuilder implements TestBuilder<CreateStoryDto> {

    private Long authorId = 1L;
    private String title = "sdfgh";
    private String content = "vbnkl";

    @Override
    public CreateStoryDto build() {
        CreateStoryDto story = new CreateStoryDto();

        story.setAuthorId(authorId);
        story.setContent(content);
        story.setTitle(title);

        return story;
    }
}

