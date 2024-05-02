package by.bsuir.publisher.model.response;

public record ErrorResponseTo (
        int code,
        String message,
        String[] errorsMessages
){

}
