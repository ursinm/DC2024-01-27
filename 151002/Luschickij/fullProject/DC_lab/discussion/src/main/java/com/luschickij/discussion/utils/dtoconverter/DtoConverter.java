package com.luschickij.discussion.utils.dtoconverter;


import com.luschickij.discussion.model.IdentifiedEntity;

public interface DtoConverter<T extends IdentifiedEntity, R extends IdentifiedEntity> {

    R convert(T t);
}
