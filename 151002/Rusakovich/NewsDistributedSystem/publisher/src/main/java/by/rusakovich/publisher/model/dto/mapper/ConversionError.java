package by.rusakovich.publisher.model.dto.mapper;

import by.rusakovich.publisher.error.exception.BaseDSException;

public class ConversionError extends BaseDSException {

    public ConversionError(){super(2, BaseDSException.codeAndMessage.get(2));}
    public ConversionError(String add){super(2, BaseDSException.codeAndMessage.get(2) + add);}
}
