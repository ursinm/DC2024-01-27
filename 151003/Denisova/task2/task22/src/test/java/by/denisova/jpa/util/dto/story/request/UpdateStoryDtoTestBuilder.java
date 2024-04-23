package by.denisova.jpa.util.dto.story.request;

import by.denisova.jpa.dto.request.UpdateStoryDto;
import by.denisova.jpa.util.TestBuilder;
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