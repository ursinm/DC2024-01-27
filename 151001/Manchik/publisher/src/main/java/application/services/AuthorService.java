package application.services;

import application.dto.AuthorRequestTo;
import application.dto.AuthorResponseTo;
import application.exceptions.NotFoundException;
import jakarta.validation.constraints.Min;

public interface AuthorService extends CrudService<AuthorRequestTo, AuthorResponseTo> {
    AuthorResponseTo getByStoryId(@Min(0) Long storyId) throws NotFoundException;
}
