package by.bsuir.vladislavmatsiushenko.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageResponseTo {
    int id;
    int issueId;
    @Size(min = 2, max = 2048)
    String content;
}
