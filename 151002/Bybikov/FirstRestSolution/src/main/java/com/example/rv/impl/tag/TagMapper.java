package com.example.rv.impl.tag;
import java.util.List;

public interface TagMapper {
    TagRequestTo tagToRequestTo(Tag tag);

    List<TagRequestTo> tagToRequestTo(Iterable<Tag> tags);

    Tag dtoToEntity(TagRequestTo tagRequestTo);

    List<Tag> dtoToEntity(Iterable<TagRequestTo> tagRequestTos);

    TagResponseTo tagToResponseTo(Tag tag);

    List<TagResponseTo> tagToResponseTo(Iterable<Tag> tags);
}
