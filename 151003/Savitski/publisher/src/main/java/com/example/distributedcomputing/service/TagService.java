package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.TagMapper;
import com.example.distributedcomputing.model.entity.Tag;
import com.example.distributedcomputing.model.request.TagRequestTo;
import com.example.distributedcomputing.model.response.TagResponseTo;
import com.example.distributedcomputing.repository.TagRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class TagService {
    private final TagRepository repository;
    private final TagMapper mapper;

    public Iterable<TagResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }
    public TagResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }
    public TagResponseTo save(@Valid TagRequestTo entity) {
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }
    public TagResponseTo update(@Valid TagRequestTo entity) {
        Long id = entity.id();
        Tag editor = mapper.dtoToEntity(entity);
        editor.setId(id);
        return mapper.entityToDto(repository.save(editor));
    }
    public void delete(@Min(0) Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
        repository.deleteById(id);
    }
}
