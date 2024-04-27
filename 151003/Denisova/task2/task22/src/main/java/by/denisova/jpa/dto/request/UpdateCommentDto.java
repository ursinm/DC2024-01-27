package by.denisova.jpa.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentDto {

    @NotNull(message = "Id must not be empty")
    @PositiveOrZero(message = "Id must be positive or zero")
    private Long id;

    @PositiveOrZero(message = "storyId must be positive or zero")
    private Long storyId;

    @Size(max = 2048, message = "Content must not exceed 2048 characters")
    @Size(min = 2, message = "Content must be at least 2 characters long")
    private String content;
}
