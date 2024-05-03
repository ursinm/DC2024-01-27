package org.example.publisher.impl.story.mapper.Impl;

import org.example.publisher.impl.user.User;
import org.example.publisher.impl.story.Story;
import org.example.publisher.impl.story.dto.StoryRequestTo;
import org.example.publisher.impl.story.dto.StoryResponseTo;
import org.example.publisher.impl.story.mapper.StoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoryMapperImpl implements StoryMapper {
    @Override
    public StoryRequestTo storyToRequestTo(Story story) {
        return new StoryRequestTo(
                story.getId(),
                story.getUser().getId(),
                story.getTitle(),
                story.getContent(),
                story.getCreated(),
                story.getModified()
        );
    }

    @Override
    public List<StoryRequestTo> storyToRequestTo(Iterable<Story> storys) {
        return StreamSupport.stream(storys.spliterator(), false)
                .map(this::storyToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Story dtoToEntity(StoryRequestTo storyRequestTo, User user) {
        return new Story(
                storyRequestTo.getId(),
                user,
                storyRequestTo.getTitle(),
                storyRequestTo.getContent(),
                storyRequestTo.getModified(),
                storyRequestTo.getCreated());
    }

    @Override
    public StoryResponseTo storyToResponseTo(Story story) {
        return new StoryResponseTo(
                story.getId(),
                story.getUser().getId(),
                story.getTitle(),
                story.getContent(),
                story.getCreated(),
                story.getModified()
        );
    }

    @Override
    public List<StoryResponseTo> storyToResponseTo(Iterable<Story> storys) {
        return StreamSupport.stream(storys.spliterator(), false)
                .map(this::storyToResponseTo)
                .collect(Collectors.toList());
    }
}
