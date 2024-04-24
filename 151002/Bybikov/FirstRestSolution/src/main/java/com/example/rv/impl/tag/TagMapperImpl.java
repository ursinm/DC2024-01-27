package com.example.rv.impl.tag;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TagMapperImpl implements TagMapper {
    @Override
    public TagRequestTo tagToRequestTo(Tag tag) {
        return new TagRequestTo(
                tag.getId(),
                tag.getName()
        );
    }

    @Override
    public List<TagRequestTo> tagToRequestTo(Iterable<Tag> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Tag dtoToEntity(TagRequestTo tagRequestTo) {
        return new Tag(
                tagRequestTo.id(),
                tagRequestTo.name()
        );
    }

    @Override
    public List<Tag> dtoToEntity(Iterable<TagRequestTo> tagRequestTos) {
        return StreamSupport.stream(tagRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TagResponseTo tagToResponseTo(Tag tag) {
        return new TagResponseTo(
                tag.getId(),
                tag.getName()
        );
    }

    @Override
    public List<TagResponseTo> tagToResponseTo(Iterable<Tag> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToResponseTo)
                .collect(Collectors.toList());
    }
}
