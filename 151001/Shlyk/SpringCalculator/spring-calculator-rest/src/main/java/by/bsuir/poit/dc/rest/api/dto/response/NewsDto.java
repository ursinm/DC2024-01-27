package by.bsuir.poit.dc.rest.api.dto.response;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.News}
 */
public record NewsDto(
    long id,
    String title,
    String content,
    Timestamp created,
    Timestamp modified,
    long userId
) implements Serializable {
}