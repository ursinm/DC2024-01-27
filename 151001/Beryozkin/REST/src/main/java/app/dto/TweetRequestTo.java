package app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 64)
    private String title;
    private String content;
    private Long authorId;
    private Long markerId;
}
