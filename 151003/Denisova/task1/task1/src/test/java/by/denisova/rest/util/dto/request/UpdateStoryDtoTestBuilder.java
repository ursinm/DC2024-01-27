package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.UpdateStoryDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class UpdateStoryDtoTestBuilder implements TestBuilder<UpdateStoryDto> {

    private Long id = 1L;
    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";

    @Override
    public UpdateStoryDto build() {
        UpdateStoryDto story = new UpdateStoryDto();

        story.setId(id);
        story.setEditorId(editorId);
        story.setContent(content);
        story.setTitle(title);

        return story;
    }
}