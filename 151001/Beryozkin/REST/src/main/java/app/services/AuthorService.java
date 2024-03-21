package app.services;

import app.dao.AuthorDao;
import app.dto.AuthorRequestTo;
import app.dto.AuthorResponseTo;
import app.entities.Author;
import app.exceptions.DeleteException;
import app.exceptions.NotFoundException;
import app.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import app.mapper.AuthorListMapper;
import app.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AuthorService {
    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    AuthorListMapper authorListMapper;

    public AuthorResponseTo getAuthorById(@Min(0) Long id) throws NotFoundException {
        Optional<Author> author = authorDao.findById(id);
        return author.map(value -> authorMapper.authorToAuthorResponse(value)).orElseThrow(() -> new NotFoundException("author not found!", 40004L));
    }

    public List<AuthorResponseTo> getAuthors() {
        return authorListMapper.toAuthorResponseList(authorDao.findAll());
    }

    public AuthorResponseTo saveAuthor(@Valid AuthorRequestTo author) {
        Author authorToSave = authorMapper.authorRequestToAuthor(author);
        return authorMapper.authorToAuthorResponse(authorDao.save(authorToSave));
    }

    public void deleteAuthor(@Min(0) Long id) throws DeleteException {
        authorDao.delete(id);
    }

    public AuthorResponseTo updateAuthor(@Valid AuthorRequestTo author) throws UpdateException {
        Author authorToUpdate = authorMapper.authorRequestToAuthor(author);
        return authorMapper.authorToAuthorResponse(authorDao.update(authorToUpdate));
    }

    public AuthorResponseTo getAuthorByTweetId(@Min(0) Long tweetId) throws NotFoundException {
        Optional<Author> author = authorDao.getAuthorByTweetId(tweetId);
        return author.map(value -> authorMapper.authorToAuthorResponse(value)).orElseThrow(() -> new NotFoundException("author not found!", 40004L));
    }
}
