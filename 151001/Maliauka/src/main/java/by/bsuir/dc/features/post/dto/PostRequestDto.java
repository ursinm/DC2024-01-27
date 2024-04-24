package by.bsuir.dc.features.post.dto;

import by.bsuir.dc.features.post.Post;

import java.io.Serializable;

/**
 * DTO for {@link Post}
 */
public record PostRequestDto(String content, Long newsId) implements Serializable {
}