package by.bsuir.springapi.model.response;

import java.time.LocalDateTime;

public record IssueResponseTo(
        Long id,
        Long creatorId,
        String title,
        String content,
        LocalDateTime created,
        LocalDateTime modified
) {}
