package by.bsuir.dc.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
