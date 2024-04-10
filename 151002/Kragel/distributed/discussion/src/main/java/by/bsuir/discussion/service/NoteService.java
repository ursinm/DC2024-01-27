package by.bsuir.discussion.service;

import by.bsuir.discussion.dto.request.NoteRequestDto;
import by.bsuir.discussion.dto.response.NoteResponseDto;
import by.bsuir.discussion.exception.ResourceNotFoundException;
import by.bsuir.discussion.model.Note;
import by.bsuir.discussion.model.NoteState;
import by.bsuir.discussion.repository.NoteRepository;
import by.bsuir.discussion.service.mapper.NoteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

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

    public List<NoteResponseDto> getAll() {
        return noteMapper.toDto(noteRepository.findAll());
    }

    public void deleteById(Long id) {
        Note entity = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        noteRepository.delete(entity);
    }

    public NoteResponseDto create(@Valid NoteRequestDto dto, Locale locale) {
        Note newEntity = noteMapper.toEntity(dto);
        newEntity.getKey().setCountry(locale);
        newEntity.getKey().setId(getTimeBasedId());
        newEntity.setState(NoteState.APPROVE);
        return noteMapper.toDto(noteRepository.save(newEntity));
    }

    public NoteResponseDto getById(Long id) {
        Note entity = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        return noteMapper.toDto(entity);
    }

    public NoteResponseDto update(@Valid NoteRequestDto dto) {
        Note entity = noteRepository
                .findByKeyId(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + dto.id() + " is not found"));
        final Note updated = noteMapper.partialUpdate(dto, entity);
        return noteMapper.toDto(noteRepository.save(updated));
    }
}
