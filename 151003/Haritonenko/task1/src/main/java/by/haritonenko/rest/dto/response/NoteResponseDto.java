package by.haritonenko.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDto {

    private Long id;
    private Long storyId;
    private String content;
}
