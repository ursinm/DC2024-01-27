package by.bsuir.dc.dto.response;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
