package by.denisova.jpa.util.dto.story.request;

import by.denisova.jpa.dto.request.CreateStoryDto;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class CreateStoryDtoTestBuilder implements TestBuilder<CreateStoryDto> {

    private Long editorId = 1L;
    private String title = "sdfgh";
    private String content = "vbnkl";

    @Override
    public CreateStoryDto build() {
        CreateStoryDto story = new CreateStoryDto();

        story.setEditorId(editorId);
        story.setContent(content);
        story.setTitle(title);

        return story;
    }
}

