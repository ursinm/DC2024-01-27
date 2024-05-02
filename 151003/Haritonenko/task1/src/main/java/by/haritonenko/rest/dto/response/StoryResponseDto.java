package by.haritonenko.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponseDto {

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;
}
