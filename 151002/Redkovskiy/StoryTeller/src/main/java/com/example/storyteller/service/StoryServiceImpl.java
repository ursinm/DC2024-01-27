package com.example.storyteller.service;

import com.example.storyteller.dto.requestDto.StoryRequestTo;
import com.example.storyteller.dto.responseDto.StoryResponseTo;
import com.example.storyteller.model.Story;
import com.example.storyteller.repository.StoryRepository;
import com.example.storyteller.util.DataDuplicationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    private final CreatorService creatorService;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository, CreatorService creatorService) {
        this.storyRepository = storyRepository;
        this.creatorService = creatorService;
    }

    @Override
    public StoryResponseTo create(StoryRequestTo dto) {
        storyRepository.findByTitle(dto.getTitle()).ifPresent(title -> {
            throw new DataDuplicationException("Creator with title: \"" + title + "\" already exists");
        });

        Story story = new Story();
        story.setCreated(LocalDateTime.now());
        requestDtoToStory(story, dto);
        storyRepository.save(story);
        return storyToResponseDto(story);
    }

    @Override
    public Iterable<StoryResponseTo> findAllDtos() {
        return StreamSupport.stream(storyRepository.findAll().spliterator(), false)
                .map(this::storyToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Story findStoryById(Long id) {
        return storyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Story with id " + id + " can't be found"));
    }

    @Override
    public StoryResponseTo findDtoById(Long id) {
        return storyToResponseDto(findStoryById(id));
    }

    @Override
    public StoryResponseTo update(StoryRequestTo dto) {
        storyRepository.findByTitle(dto.getTitle()).ifPresent(title -> {
            throw new DataDuplicationException("Creator with title: \"" + title + "\" already exists");
        });

        Story story = findStoryById(dto.getId());
        requestDtoToStory(story, dto);
        storyRepository.save(story);
        return storyToResponseDto(story);
    }

    @Override
    public void delete(Long id) {
        Story story = findStoryById(id);
        storyRepository.delete(story);
    }

    void requestDtoToStory(Story story, StoryRequestTo dto) {
        story.setTitle(dto.getTitle());
        story.setModified(LocalDateTime.now());
        story.setContent(dto.getContent());
        story.setCreator(creatorService.findCreatorById(dto.getCreatorId()));
    }

    StoryResponseTo storyToResponseDto(Story story) {
        StoryResponseTo dto = new StoryResponseTo();
        dto.setId(story.getId());
        dto.setTitle(story.getTitle());
        dto.setContent(story.getContent());
        dto.setCreated(story.getCreated().toString());
        dto.setModified(story.getModified().toString());
        dto.setCreatorId(story.getCreator().getId());
        return dto;
    }
}
