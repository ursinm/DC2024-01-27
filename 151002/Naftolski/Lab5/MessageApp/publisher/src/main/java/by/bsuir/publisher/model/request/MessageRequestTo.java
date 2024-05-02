package by.bsuir.publisher.model.request;

import org.hibernate.validator.constraints.Length;

public record MessageRequestTo(
        Long id,
        Long storyId,
        @Length(min = 2, max = 2048)
        String content)
{}
