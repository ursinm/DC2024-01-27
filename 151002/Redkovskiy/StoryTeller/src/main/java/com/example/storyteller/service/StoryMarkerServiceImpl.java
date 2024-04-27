package com.example.storyteller.service;

import com.example.storyteller.model.Marker;
import com.example.storyteller.model.Story;
import com.example.storyteller.model.StoryMarker;
import com.example.storyteller.repository.MarkerRepository;
import com.example.storyteller.repository.StoryMarkerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryMarkerServiceImpl implements StoryMarkerService {

    private final StoryMarkerRepository storyMarkerRepository;

    @Autowired
    public StoryMarkerServiceImpl(StoryMarkerRepository storyMarkerRepository) {
        this.storyMarkerRepository = storyMarkerRepository;
    }

    @Override
    public Optional<StoryMarker> findByStoryAndMarker(Story story, Marker marker) {
        return storyMarkerRepository.findStoryMarkerByStoryAndMarker(story, marker);
    }

    @Override
    public void save(StoryMarker storyMarker) {
        storyMarkerRepository.save(storyMarker);
    }

    @Override
    public StoryMarker embedEntities(Story story, Marker marker) {
        return new StoryMarker(story, marker);
    }

    @Override
    public void updateStory(StoryMarker storyMarker, Story story) {
        storyMarker.setStory(story);
        save(storyMarker);
    }

    @Override
    public void deleteAllByMarker(Marker marker) {
        storyMarkerRepository.deleteAll(storyMarkerRepository.findAllByMarker(marker));
    }
}
