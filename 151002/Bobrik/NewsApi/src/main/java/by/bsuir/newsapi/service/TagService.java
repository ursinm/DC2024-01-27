package by.bsuir.newsapi.service;

import by.bsuir.newsapi.dao.impl.TagRepository;
import by.bsuir.newsapi.model.request.TagRequestTo;
import by.bsuir.newsapi.model.response.TagResponseTo;
import by.bsuir.newsapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.newsapi.service.exceptions.ResourceStateException;
import by.bsuir.newsapi.service.mapper.TagMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class TagService implements RestService <TagRequestTo, TagResponseTo> {
    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    @Override
    public List<TagResponseTo> findAll() {
        return tagMapper.getListResponseTo(tagRepository.getAll());
    }

    @Override
    public TagResponseTo findById(Long id) {
        return tagMapper.getResponseTo(tagRepository
                .getBy(id)
                .orElseThrow(() -> tagNotFoundException(id)));
    }

    @Override
    public TagResponseTo create(TagRequestTo tagTo) {
        return tagRepository
                .save(tagMapper.getTag(tagTo))
                .map(tagMapper::getResponseTo)
                .orElseThrow(TagService::tagStateException);
    }

    @Override
    public TagResponseTo update(TagRequestTo tagTo) {
        tagRepository
                .getBy(tagMapper.getTag(tagTo).getId())
                .orElseThrow(() -> tagNotFoundException(tagMapper.getTag(tagTo).getId()));
        return tagRepository
                .update(tagMapper.getTag(tagTo))
                .map(tagMapper::getResponseTo)
                .orElseThrow(TagService::tagStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!tagRepository.removeById(id)) {
            throw tagNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException tagNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find tag with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 33);
    }

    private static ResourceStateException tagStateException() {
        return new ResourceStateException("Failed to create/update tag with specified credentials", HttpStatus.CONFLICT.value() * 100 + 34);
    }
}
