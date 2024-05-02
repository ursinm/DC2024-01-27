package app.services;

import app.dto.AuthorRequestTo;
import app.dto.AuthorResponseTo;
import app.exceptions.DeleteException;
import app.exceptions.DuplicationException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import app.mapper.AuthorListMapper;
import app.mapper.AuthorMapper;
import app.repository.AuthorRepository;
import app.entities.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
@CacheConfig(cacheNames = "authorsCache")
public class AuthorService {
    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    AuthorRepository authorDao;
    @Autowired
    AuthorListMapper authorListMapper;

    @Cacheable(cacheNames = "authors", key = "#id", unless = "#result == null")
    public AuthorResponseTo getAuthorById(@Min(0) Long id) throws NotFoundException {
        Optional<Author> author = authorDao.findById(id);
        return author.map(value -> authorMapper.authorToAuthorResponse(value)).orElseThrow(() -> new NotFoundException("Author not found!", 40004L));
    }

    @Cacheable(cacheNames = "authors")
    public List<AuthorResponseTo> getAuthors(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Author> authors = authorDao.findAll(pageable);
        return authorListMapper.toAuthorResponseList(authors.toList());
    }
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorResponseTo saveAuthor(@Valid AuthorRequestTo author) throws DuplicationException {
        Author authorToSave = authorMapper.authorRequestToAuthor(author);
        if (authorDao.existsByLogin(authorToSave.getLogin())) {
            throw new DuplicationException("Login duplication", 40005L);
        }
        return authorMapper.authorToAuthorResponse(authorDao.save(authorToSave));
    }
    @Caching(evict = { @CacheEvict(cacheNames = "authors", key = "#id"),
            @CacheEvict(cacheNames = "authors", allEntries = true) })
    public void deleteAuthor(@Min(0) Long id) throws DeleteException {
        if (!authorDao.existsById(id)) {
            throw new DeleteException("Author not found!", 40004L);
        } else {
            authorDao.deleteById(id);
        }
    }
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorResponseTo updateAuthor(@Valid AuthorRequestTo author) throws UpdateException {
        Author authorToUpdate = authorMapper.authorRequestToAuthor(author);
        if (!authorDao.existsById(authorToUpdate.getId())) {
            throw new UpdateException("Author not found!", 40004L);
        } else {
            return authorMapper.authorToAuthorResponse(authorDao.save(authorToUpdate));
        }
    }

    public AuthorResponseTo getAuthorByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        Author author = authorDao.findAuthorByTweetId(tweetId);
        return authorMapper.authorToAuthorResponse(author);
    }
}
