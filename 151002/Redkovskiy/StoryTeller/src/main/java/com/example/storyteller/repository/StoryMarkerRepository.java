package com.example.storyteller.repository;

import com.example.storyteller.model.Marker;
import com.example.storyteller.model.Story;
import com.example.storyteller.model.StoryMarker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StoryMarkerRepository extends CrudRepository<StoryMarker, Long> {

    Optional<StoryMarker> findStoryMarkerByStoryAndMarker(Story story, Marker marker);

    List<StoryMarker> findAllByMarker(Marker marker);
}
