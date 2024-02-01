package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.model.IdentifiedEntity;

public interface DtoConverter<T extends IdentifiedEntity, R extends IdentifiedEntity> {

    R convert(T t);
}
