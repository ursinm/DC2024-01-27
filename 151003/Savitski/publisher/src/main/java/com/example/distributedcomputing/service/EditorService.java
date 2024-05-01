package com.example.distributedcomputing.service;

import com.example.distributedcomputing.exceptions.EditorAlreadyExist;
import com.example.distributedcomputing.exceptions.NotFoundException;
import com.example.distributedcomputing.mapper.EditorMapper;
import com.example.distributedcomputing.model.entity.Editor;
import com.example.distributedcomputing.model.request.EditorRequestTo;
import com.example.distributedcomputing.model.response.EditorResponseTo;
import com.example.distributedcomputing.repository.EditorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@CacheConfig(cacheNames = "editorsCache")
public class EditorService {
    private final EditorRepository repository;
    private final EditorMapper mapper;

    @Cacheable(cacheNames = "editors")
    public Iterable<EditorResponseTo> getAll() {
        return mapper.entityToDto(repository.findAll());
    }

    @Cacheable(cacheNames = "editors", key = "#id", unless = "#result == null")
    public EditorResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException(404L, "Editor is not found"));
    }

    @CacheEvict(cacheNames = "editors", allEntries = true)
    public EditorResponseTo save(@Valid EditorRequestTo entity) {
        if(repository.findByLogin(entity.login()).isPresent()) {
            throw new EditorAlreadyExist(403L, "Editor with this login is already exist");
        }
        return mapper.entityToDto(repository.save(mapper.dtoToEntity(entity)));
    }

    @CacheEvict(cacheNames = "editors", allEntries = true)
    public EditorResponseTo update(@Valid EditorRequestTo requestTo) {
        Long id = requestTo.id();
        Editor entity = mapper.dtoToEntity(requestTo);
        entity.setId(id);
        return mapper.entityToDto(repository.save(entity));

    }

    @Caching(evict = { @CacheEvict(cacheNames = "editors", key = "#id"),
            @CacheEvict(cacheNames = "editors", allEntries = true) })
    public void delete(@Min(0) Long id) {
      repository.findById(id).orElseThrow(() -> new NotFoundException(404L, "Not founded"));
      repository.deleteById(id);
    }
}
