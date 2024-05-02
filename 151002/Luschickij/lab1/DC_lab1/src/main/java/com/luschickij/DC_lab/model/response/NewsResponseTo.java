package com.luschickij.DC_lab.model.response;

import java.time.LocalDateTime;

public record NewsResponseTo(
        Long id,
        Long creatorId,
        String title,
        String content,
        LocalDateTime created,
        LocalDateTime modified
) {
}
