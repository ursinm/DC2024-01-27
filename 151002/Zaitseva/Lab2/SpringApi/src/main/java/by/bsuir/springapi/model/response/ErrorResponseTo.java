package by.bsuir.springapi.model.response;

public record ErrorResponseTo(
    int errorCode,
    String errorMessage,

    String[] errors
) {}