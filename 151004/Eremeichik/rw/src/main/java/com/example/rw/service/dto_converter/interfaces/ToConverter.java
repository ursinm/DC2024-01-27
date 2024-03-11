package com.example.rw.service.dto_converter.interfaces;

import com.example.rw.exception.model.dto_converting.ToConvertingException;
import com.example.rw.model.entity.interfaces.EntityModel;

public interface ToConverter<E extends EntityModel<?>, A, B> {

    E convertToEntity(A requestTo) throws ToConvertingException;

    B convertToDto(E entity) throws ToConvertingException;
}
