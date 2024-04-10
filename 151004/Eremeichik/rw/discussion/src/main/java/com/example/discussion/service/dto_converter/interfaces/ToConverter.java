package com.example.discussion.service.dto_converter.interfaces;

import com.example.discussion.exception.model.dto_converting.ToConvertingException;
import com.example.discussion.model.entity.interfaces.EntityModel;

public interface ToConverter<E extends EntityModel<?>, A, B> {

    E convertToEntity(A requestTo) throws ToConvertingException;

    B convertToDto(E entity) throws ToConvertingException;
}
