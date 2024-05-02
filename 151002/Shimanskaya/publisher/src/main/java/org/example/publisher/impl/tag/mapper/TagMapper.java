package org.example.publisher.impl.tag.mapper;
import org.example.publisher.impl.issue.Issue;
import org.example.publisher.impl.tag.Tag;
import org.example.publisher.impl.tag.dto.TagRequestTo;
import org.example.publisher.impl.tag.dto.TagResponseTo;

import java.util.List;

public interface TagMapper {
    TagRequestTo tagToRequestTo(Tag tag);

    List<TagRequestTo> tagToRequestTo(Iterable<Tag> tags);

    Tag dtoToEntity(TagRequestTo tagRequestTo, List<Issue> tweets);

    TagResponseTo tagToResponseTo(Tag tag);

    List<TagResponseTo> tagToResponseTo(Iterable<Tag> tags);
}
