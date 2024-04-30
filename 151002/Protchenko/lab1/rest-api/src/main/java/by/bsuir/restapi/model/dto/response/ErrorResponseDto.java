package by.bsuir.restapi.model.dto.response;

public record ErrorResponseDto(
        Integer errorCode,
        String errorMessage
) {
}
