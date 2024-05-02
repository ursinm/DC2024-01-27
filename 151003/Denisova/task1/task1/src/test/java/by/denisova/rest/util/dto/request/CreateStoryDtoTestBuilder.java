package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.CreateStoryDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class CreateStoryDtoTestBuilder implements TestBuilder<CreateStoryDto> {

    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public CreateStoryDto build() {
        CreateStoryDto story = new CreateStoryDto();

        story.setEditorId(editorId);
        story.setContent(content);
        story.setTitle(title);

        return story;
    }
}

