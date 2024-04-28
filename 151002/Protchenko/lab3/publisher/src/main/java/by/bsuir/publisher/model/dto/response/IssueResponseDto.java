package by.bsuir.publisher.model.dto.response;

import java.time.Instant;

public record IssueResponseDto(
        Long id,
        Long authorId,
        String title,
        String content,
        Instant created,
        Instant modified
) {
}
