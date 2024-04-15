package by.bsuir.dc.publisher.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private Long id;

    Long storyId;

    private String content;

    private String country;

}
