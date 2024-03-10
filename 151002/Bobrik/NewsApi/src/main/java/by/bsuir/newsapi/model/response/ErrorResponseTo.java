package by.bsuir.newsapi.model.response;

public record ErrorResponseTo(
    int errorCode,
    String errorMessage,

    String[] errors
) {}