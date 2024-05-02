package application.services.impl;

import application.dto.AuthorRequestTo;
import application.dto.AuthorResponseTo;
import application.entites.Author;
import application.exceptions.DeleteException;
import application.exceptions.DuplicationException;
import application.exceptions.NotFoundException;
import application.exceptions.UpdateException;
import application.mappers.AuthorListMapper;
import application.mappers.AuthorMapper;
import application.repository.AuthorRepository;
import application.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImplAuthorService implements AuthorService {

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorListMapper authorListMapper;

    @Override
    @Cacheable(cacheNames = "authors", key = "#id", unless = "#result == null")
    public AuthorResponseTo getById(Long id) throws NotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(value -> authorMapper.toAuthorResponse(value)).orElseThrow(() -> new NotFoundException("Author not found", 40004L));
    }

    @Override
    @Cacheable(cacheNames = "authors")
    public List<AuthorResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Author> authors = authorRepository.findAll(pageable);
        return authorListMapper.toAuthorResponseList(authors.toList());
    }

    @Override
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorResponseTo save(@Valid AuthorRequestTo requestTo) {
        Author userToSave = authorMapper.toAuthor(requestTo);
        if (authorRepository.existsByLogin(requestTo.getLogin())) {
            throw new DuplicationException("Login duplication", 40005L);
        }
        return authorMapper.toAuthorResponse(authorRepository.save(userToSave));
    }

    @Override
    @CacheEvict(cacheNames = "authors", key = "#id")
    public void delete(Long id) throws DeleteException {
        if (!authorRepository.existsById(id)) {
            throw new DeleteException("Author not found!", 40004L);
        } else {
            authorRepository.deleteById(id);
        }
    }

    @Override
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorResponseTo update(@Valid AuthorRequestTo requestTo) throws UpdateException {
        Author authorToUpdate = authorMapper.toAuthor(requestTo);
        if (!authorRepository.existsById(authorToUpdate.getId())) {
            throw new UpdateException("Author not found!", 40004L);
        } else {
            return authorMapper.toAuthorResponse(authorRepository.save(authorToUpdate));
        }
    }

    @Override
    public AuthorResponseTo getByStoryId(Long storyId) throws NotFoundException {
        Optional<Author> editor = authorRepository.findAuthorByStory(storyId);
        return editor.map(value -> authorMapper.toAuthorResponse(value)).orElseThrow(() -> new NotFoundException("Author not found!", 40004L));
    }
}
