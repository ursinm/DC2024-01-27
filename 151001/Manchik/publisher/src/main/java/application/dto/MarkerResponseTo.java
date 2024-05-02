package application.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerResponseTo implements Serializable {
    private Long id;
    private String name;
    private Long storyId;
}
