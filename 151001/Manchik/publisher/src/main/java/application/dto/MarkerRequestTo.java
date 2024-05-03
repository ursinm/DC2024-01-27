package application.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerRequestTo implements Serializable {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
    private Long storyId;
}
