package com.luschickij.publisher.service;

import com.luschickij.publisher.dto.creator.CreatorRequestTo;
import com.luschickij.publisher.dto.creator.CreatorResponseTo;
import com.luschickij.publisher.model.Creator;
import com.luschickij.publisher.repository.CreatorRepository;
import com.luschickij.publisher.repository.jpa.JpaCreatorRepository;
import com.luschickij.publisher.utils.dtoconverter.CreatorRequestDtoConverter;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
public class CreatorService extends CommonRestService<Creator, CreatorRequestTo, CreatorResponseTo, Long> {

    public CreatorService(
            CreatorRepository creatorRepository,
            CreatorRequestDtoConverter dtoConverter) {
        super(creatorRepository, dtoConverter);
    }

    @Override
    protected Optional<CreatorResponseTo> mapResponseTo(Creator creator) {
        return Optional.ofNullable(CreatorResponseTo.builder()
                .id(creator.getId())
                .login(creator.getLogin())
                .hashedPassword(creator.getPassword()) //TODO: hash
                .firstname(creator.getFirstname())
                .lastname(creator.getLastname())
                .build());
    }

    @Override
    protected void update(Creator u1, Creator u2) {
        u1.setFirstname(u2.getFirstname());
        u1.setLastname(u2.getLastname());
        u1.setLogin(u2.getLogin());
        u1.setPassword(u2.getPassword());
    }
}
