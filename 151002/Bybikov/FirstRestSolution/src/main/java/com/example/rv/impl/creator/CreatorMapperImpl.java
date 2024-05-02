package com.example.rv.impl.creator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CreatorMapperImpl implements CreatorMapper {
    @Override
    public CreatorRequestTo editorToRequestTo(Creator creator) {
        return new CreatorRequestTo(creator.getId(),
                creator.getLogin(),
                creator.getPassword(),
                creator.getFirstname(),
                creator.getLastname());
    }

    @Override
    public List<CreatorRequestTo> editorToRequestTo(Iterable<Creator> editors) {
        return StreamSupport.stream(editors.spliterator(), false)
                .map(this::editorToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Creator dtoToEntity(CreatorRequestTo creatorRequestTo) {
        return new Creator(creatorRequestTo.id(),
                creatorRequestTo.login(),
                creatorRequestTo.password(),
                creatorRequestTo.firstname(),
                creatorRequestTo.lastname());
    }

    @Override
    public List<Creator> dtoToEntity(Iterable<CreatorRequestTo> editorRequestTos) {
        return StreamSupport.stream(editorRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CreatorResponseTo editorToResponseTo(Creator creator) {
        return new CreatorResponseTo(creator.getId(),
                creator.getLogin(),
                creator.getFirstname(),
                creator.getLastname());
    }

    @Override
    public List<CreatorResponseTo> editorToResponseTo(Iterable<Creator> editors) {
        return StreamSupport.stream(editors.spliterator(), false)
                .map(this::editorToResponseTo)
                .collect(Collectors.toList());
    }
}
