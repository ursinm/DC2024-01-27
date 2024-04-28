package by.bsuir.messageapp.model.response;

public record ErrorResponseTo (
        int code,
        String message,
        String[] errorsMessages
){

}
