package by.denisova.rest.util.dto.response;

import by.denisova.rest.dto.response.StoryResponseDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(staticName = "story")
@With
public class StoryResponseDtoTestBuilder implements TestBuilder<StoryResponseDto> {

    private Long id = 1L;
    private Long editorId = 1L;
    private String title = "title";
    private String content = "bvcdsx";
    private LocalDateTime created = LocalDateTime.of(2024, 1, 1, 4, 1);
    private LocalDateTime modified = LocalDateTime.of(2024, 2, 1, 4, 1);

    @Override
    public StoryResponseDto build() {
        StoryResponseDto story = new StoryResponseDto();

        story.setId(id);
        story.setEditorId(editorId);
        story.setContent(content);
        story.setTitle(title);
        story.setCreated(created);
        story.setModified(modified);

        return story;
    }
}
