package by.bsuir.discussion.dto.response;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
