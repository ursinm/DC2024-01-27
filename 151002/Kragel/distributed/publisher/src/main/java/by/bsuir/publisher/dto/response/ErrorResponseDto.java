package by.bsuir.publisher.dto.response;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
