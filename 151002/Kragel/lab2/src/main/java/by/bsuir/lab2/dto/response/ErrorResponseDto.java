package by.bsuir.lab2.dto.response;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
