package com.example.distributedcomputing.service;


import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.exceptions.AlreadyExist;
import com.example.distributedcomputing.mapper.StoryMapper;
import com.example.distributedcomputing.model.entity.Story;
import com.example.distributedcomputing.model.request.StoryRequestTo;
import com.example.distributedcomputing.model.response.StoryResponseTo;
import com.example.distributedcomputing.repository.StoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class StoryService {
    private final EditorService editorService;
    private final StoryRepository repository;
    private final StoryMapper mapper;

    public Iterable<StoryResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public StoryResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public StoryResponseTo save(@Valid StoryRequestTo entity) {
        editorService.getById(entity.editorId());
        if (repository.findByTitle(entity.title()).isPresent()) {
            throw new AlreadyExist(403L, "Story with this title already exist");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public StoryResponseTo update(@Valid StoryRequestTo entity) {
        Long id = entity.id();
        Story editor = mapper.dtoToEntity(entity);
        editor.setId(id);
        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
