package by.bsuir.dc.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
