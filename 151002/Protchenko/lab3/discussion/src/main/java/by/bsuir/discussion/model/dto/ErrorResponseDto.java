package by.bsuir.discussion.model.dto;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
