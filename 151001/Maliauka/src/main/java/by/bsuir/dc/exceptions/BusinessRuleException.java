package by.bsuir.dc.exceptions;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String errorMessage){
        super(errorMessage);
    }
}
