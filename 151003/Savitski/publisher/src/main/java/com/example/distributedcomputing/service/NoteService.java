package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.NoteMapper;
import com.example.distributedcomputing.model.entity.Note;
import com.example.distributedcomputing.model.request.NoteRequestTo;
import com.example.distributedcomputing.model.response.NoteResponseTo;
import com.example.distributedcomputing.repository.NoteRepository;
import com.example.distributedcomputing.repository.StoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class NoteService {
    private final NoteRepository repository;
    private final NoteMapper mapper;
    private final StoryRepository storyRepository;

    public Iterable<NoteResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public NoteResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public NoteResponseTo save(@Valid NoteRequestTo entity) {
        if (storyRepository.findById(entity.getStoryId()).isEmpty()) {
            throw new NotFoundException(404L, "Story with this ID is not found");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public NoteResponseTo update(@Valid NoteRequestTo entity) {
        Long id = entity.getId();
        Note editor = mapper.dtoToEntity(entity);
        editor.setId(id);

        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
