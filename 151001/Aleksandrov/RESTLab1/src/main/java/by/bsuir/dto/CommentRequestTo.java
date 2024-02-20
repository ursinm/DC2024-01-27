package by.bsuir.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestTo {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 2048)
    private String content;
    private Long issueId;
}
