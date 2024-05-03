package by.bsuir.discussion.service;

import by.bsuir.discussion.exception.ResourceNotFoundException;
import by.bsuir.discussion.model.dto.PostRequestDto;
import by.bsuir.discussion.model.dto.PostResponseDto;
import by.bsuir.discussion.model.entity.Post;
import by.bsuir.discussion.model.mapper.PostMapper;
import by.bsuir.discussion.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository noteRepository;
    private final PostMapper noteMapper;

    private static final SecureRandom random;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }

    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    public List<PostResponseDto> getAll() {
        return noteMapper.toDto(noteRepository.findAll());
    }

    public void delete(Long id) {
        Post entity = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " is not found"));
        noteRepository.delete(entity);
    }

    public PostResponseDto create(@Valid PostRequestDto dto, Locale locale) {
        Post newEntity = noteMapper.toEntity(dto);
        newEntity.getKey().setCountry(locale);
        newEntity.getKey().setId(getTimeBasedId());
        newEntity.getKey().setIssueId(dto.issueId());
        return noteMapper.toDto(noteRepository.save(newEntity));
    }

    public PostResponseDto get(Long id) {
        Post entity = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " is not found"));
        return noteMapper.toDto(entity);
    }

    public PostResponseDto update(@Valid PostRequestDto dto) {
        Post entity = noteRepository
                .findByKeyId(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + dto.id() + " is not found"));
        final Post updated = noteMapper.partialUpdate(dto, entity);
        return noteMapper.toDto(noteRepository.save(updated));
    }
}
