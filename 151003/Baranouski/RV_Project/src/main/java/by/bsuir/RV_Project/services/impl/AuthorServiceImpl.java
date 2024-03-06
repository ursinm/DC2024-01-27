package by.bsuir.RV_Project.services.impl;

import by.bsuir.RV_Project.domain.Author;
import by.bsuir.RV_Project.dto.requests.AuthorRequestDto;
import by.bsuir.RV_Project.dto.requests.converters.AuthorRequestConverter;
import by.bsuir.RV_Project.dto.responses.AuthorResponseDto;
import by.bsuir.RV_Project.dto.responses.converters.AuthorResponseConverter;
import by.bsuir.RV_Project.dto.responses.converters.CollectionAuthorResponseConverter;
import by.bsuir.RV_Project.exceptions.Messages;
import by.bsuir.RV_Project.exceptions.NoEntityExistsException;
import by.bsuir.RV_Project.repositories.AuthorRepository;
import by.bsuir.RV_Project.services.AuthorService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorRequestConverter authorRequestConverter;
    private final AuthorResponseConverter authorResponseConverter;
    private final CollectionAuthorResponseConverter collectionAuthorResponseConverter;

    @Override
    @Validated
    public AuthorResponseDto create(@Valid @NonNull AuthorRequestDto dto) throws EntityExistsException {
        Optional<Author> author = dto.getId() == null ? Optional.empty() : authorRepository.findById(dto.getId());
        if (author.isEmpty()) {
            return authorResponseConverter.toDto(authorRepository.save(authorRequestConverter.fromDto(dto)));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<AuthorResponseDto> read(@NonNull Long id) {
        return authorRepository.findById(id).flatMap(author -> Optional.of(
                authorResponseConverter.toDto(author)));
    }

    @Override
    @Validated
    public AuthorResponseDto update(@Valid @NonNull AuthorRequestDto dto) throws NoEntityExistsException {
        Optional<Author> author = dto.getId() == null || authorRepository.findById(dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(authorRequestConverter.fromDto(dto));
        return authorResponseConverter.toDto(authorRepository.save(author.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Author> author = authorRepository.findById(id);
        authorRepository.deleteById(author.map(Author::getId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
        return author.get().getId();
    }

    @Override
    public List<AuthorResponseDto> readAll() {
        return collectionAuthorResponseConverter.toListDto(authorRepository.findAll());
    }
}
