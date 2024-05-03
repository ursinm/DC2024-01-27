package application.dto;

import application.entites.utils.JsonCDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryRequestTo implements Serializable {
    private Long id;
    @NotBlank
    @Size(min =  2, max = 64)
    private String title;
    @NotBlank
    @Size(min =  4, max = 2048)
    @JsonDeserialize(using = JsonCDeserializer.class)
    private String content;
    private Long authorId;
    private Long markerId;
}
