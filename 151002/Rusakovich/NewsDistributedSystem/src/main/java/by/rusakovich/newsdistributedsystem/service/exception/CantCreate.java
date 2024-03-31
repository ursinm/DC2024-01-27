package by.rusakovich.newsdistributedsystem.service.exception;

import by.rusakovich.newsdistributedsystem.error.exception.BaseDSException;

public class CantCreate extends BaseDSException{
    public CantCreate(){super(1, BaseDSException.codeAndMessage.get(1));}
    public CantCreate(String add){super(1, BaseDSException.codeAndMessage.get(1) + add);}
    public CantCreate(Object id){super(1, BaseDSException.codeAndMessage.get(1) + "With id: " + id.toString());}
}
