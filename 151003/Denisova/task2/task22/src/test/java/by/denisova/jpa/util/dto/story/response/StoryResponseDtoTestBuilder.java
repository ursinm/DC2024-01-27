package by.denisova.jpa.util.dto.story.response;

import by.denisova.jpa.dto.response.StoryResponseDto;
import by.denisova.jpa.util.TestBuilder;
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
    private LocalDateTime created = LocalDateTime.parse("2024-03-18T00:50:05.464483");
    private LocalDateTime modified = LocalDateTime.parse("2024-03-18T00:50:05.464483");

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
