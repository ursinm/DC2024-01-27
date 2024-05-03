package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.creator.CreatorResponseTo;
import com.luschickij.publisher.model.Creator;
import org.springframework.stereotype.Component;

@Component
public class CreatorResponseDtoConverter implements DtoConverter<Creator, CreatorResponseTo> {

    @Override
    public CreatorResponseTo convert(Creator creator) {
        return CreatorResponseTo.builder()
                .id(creator.getId())
                .login(creator.getLogin())
                .hashedPassword(creator.getPassword())
                .firstname(creator.getFirstname())
                .lastname(creator.getLastname())
                .build();
    }
}
