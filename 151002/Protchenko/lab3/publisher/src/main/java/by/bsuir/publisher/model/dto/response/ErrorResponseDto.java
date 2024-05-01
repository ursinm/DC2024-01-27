package by.bsuir.publisher.model.dto.response;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
