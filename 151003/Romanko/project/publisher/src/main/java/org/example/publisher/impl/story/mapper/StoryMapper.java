package org.example.publisher.impl.story.mapper;

import org.example.publisher.impl.user.User;
import org.example.publisher.impl.story.Story;
import org.example.publisher.impl.story.dto.StoryRequestTo;
import org.example.publisher.impl.story.dto.StoryResponseTo;

import java.util.List;

public interface StoryMapper {

    StoryRequestTo storyToRequestTo(Story story);

    List<StoryRequestTo> storyToRequestTo(Iterable<Story> storys);

    Story dtoToEntity(StoryRequestTo storyRequestTo, User user);

    StoryResponseTo storyToResponseTo(Story story);

    List<StoryResponseTo> storyToResponseTo(Iterable<Story> storys);
}
