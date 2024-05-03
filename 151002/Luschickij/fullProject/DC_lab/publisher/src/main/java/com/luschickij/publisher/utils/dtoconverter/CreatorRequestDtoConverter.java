package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.creator.CreatorRequestTo;
import com.luschickij.publisher.model.Creator;
import org.springframework.stereotype.Component;

@Component
public class CreatorRequestDtoConverter implements DtoConverter<CreatorRequestTo, Creator> {

    @Override
    public Creator convert(CreatorRequestTo creatorRequestTo) {
        return Creator.builder()
                .id(creatorRequestTo.getId())
                .login(creatorRequestTo.getLogin())
                .password(creatorRequestTo.getPassword()) // TODO: Hash password
                .firstname(creatorRequestTo.getFirstname())
                .lastname(creatorRequestTo.getLastname())
                .build();
    }
}
