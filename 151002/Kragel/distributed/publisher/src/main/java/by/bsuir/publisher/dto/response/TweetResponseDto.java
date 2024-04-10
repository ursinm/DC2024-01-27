package by.bsuir.publisher.dto.response;

import java.time.Instant;

public record TweetResponseDto(
        Long id,
        Long creatorId,
        String title,
        String content,
        Instant created,
        Instant modified
){
}

