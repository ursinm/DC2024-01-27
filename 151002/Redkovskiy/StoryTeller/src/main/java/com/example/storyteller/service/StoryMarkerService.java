package com.example.storyteller.service;

import com.example.storyteller.model.Marker;
import com.example.storyteller.model.Story;
import com.example.storyteller.model.StoryMarker;

import java.util.Optional;

public interface StoryMarkerService {

    Optional<StoryMarker> findByStoryAndMarker(Story story, Marker marker);

    void save(StoryMarker storyMarker);

    StoryMarker embedEntities(Story story, Marker marker);

    void updateStory(StoryMarker storyMarker, Story story);

    void deleteAllByMarker(Marker marker);
}
