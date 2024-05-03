package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.model.IdentifiedEntity;

public interface DtoConverter<T extends IdentifiedEntity, R extends IdentifiedEntity> {

    R convert(T t);
}
