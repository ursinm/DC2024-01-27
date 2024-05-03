package by.bsuir.springapi.service;

import by.bsuir.springapi.dao.impl.CreatorRepository;
import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.CreatorMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreatorService implements RestService<CreatorRequestTo, CreatorResponseTo> {
    private final CreatorRepository creatorRepository;

    private final CreatorMapper creatorMapper;

    @Override
    public List<CreatorResponseTo> findAll() {
        return creatorMapper.getListResponseTo(creatorRepository.getAll());
    }

    @Override
    public CreatorResponseTo findById(Long id) {
        return creatorMapper.getResponseTo(creatorRepository
                .getBy(id)
                .orElseThrow(() -> editorNotFoundException(id)));
    }

    @Override
    public CreatorResponseTo create(CreatorRequestTo editorTo) {
        return creatorRepository
                .save(creatorMapper.getCreator(editorTo))
                .map(creatorMapper::getResponseTo)
                .orElseThrow(CreatorService::editorStateException);
    }

    @Override
    public CreatorResponseTo update(CreatorRequestTo editorTo) {
        creatorRepository
                .getBy(creatorMapper.getCreator(editorTo).getId())
                .orElseThrow(() -> editorNotFoundException(creatorMapper.getCreator(editorTo).getId()));
        return creatorRepository
                .update(creatorMapper.getCreator(editorTo))
                .map(creatorMapper::getResponseTo)
                .orElseThrow(CreatorService::editorStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!creatorRepository.removeById(id)) {
            throw editorNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException editorNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find editor with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 23);
    }

    private static ResourceStateException editorStateException() {
        return new ResourceStateException("Failed to create/update editor with specified credentials", HttpStatus.CONFLICT.value() * 100 + 24);
    }
}
