package org.example.publisher.impl.creator.mapper.Impl;

import org.example.publisher.impl.creator.Creator;
import org.example.publisher.impl.creator.dto.CreatorRequestTo;
import org.example.publisher.impl.creator.dto.CreatorResponseTo;
import org.example.publisher.impl.creator.mapper.CreatorMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CreatorMapperImpl implements CreatorMapper {
    @Override
    public CreatorRequestTo creatorToRequestTo(Creator creator) {
        return new CreatorRequestTo(creator.getId(),
                creator.getLogin(),
                creator.getPassword(),
                creator.getFirstname(),
                creator.getLastname());
    }

    @Override
    public List<CreatorRequestTo> creatorToRequestTo(Iterable<Creator> creators) {
        return StreamSupport.stream(creators.spliterator(), false)
                .map(this::creatorToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Creator dtoToEntity(CreatorRequestTo creatorRequestTo) {
        return new Creator(creatorRequestTo.getId(),
                creatorRequestTo.getLogin(),
                creatorRequestTo.getPassword(),
                creatorRequestTo.getFirstname(),
                creatorRequestTo.getLastname());
    }

    @Override
    public List<Creator> dtoToEntity(Iterable<CreatorRequestTo> creatorRequestTos) {
        return StreamSupport.stream(creatorRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CreatorResponseTo creatorToResponseTo(Creator creator) {
        return new CreatorResponseTo(creator.getId(),
                creator.getLogin(),
                creator.getFirstname(),
                creator.getLastname());
    }

    @Override
    public List<CreatorResponseTo> creatorToResponseTo(Iterable<Creator> creators) {
        return StreamSupport.stream(creators.spliterator(), false)
                .map(this::creatorToResponseTo)
                .collect(Collectors.toList());
    }
}
