package org.example.publisher.impl.tag.mapper;
import org.example.publisher.impl.tag.Tag;
import org.example.publisher.impl.tag.dto.TagRequestTo;
import org.example.publisher.impl.tag.dto.TagResponseTo;
import org.example.publisher.impl.tweet.Tweet;

import java.util.List;

public interface TagMapper {
    TagRequestTo tagToRequestTo(Tag tag);

    List<TagRequestTo> tagToRequestTo(Iterable<Tag> tags);

    Tag dtoToEntity(TagRequestTo tagRequestTo, List<Tweet> tweets);

    TagResponseTo tagToResponseTo(Tag tag);

    List<TagResponseTo> tagToResponseTo(Iterable<Tag> tags);
}
