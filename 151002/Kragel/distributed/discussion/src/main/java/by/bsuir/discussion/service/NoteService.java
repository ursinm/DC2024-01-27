package by.bsuir.discussion.service;

import by.bsuir.discussion.dto.request.NoteRequestDto;
import by.bsuir.discussion.dto.response.NoteResponseDto;
import by.bsuir.discussion.exception.ResourceNotFoundException;
import by.bsuir.discussion.model.Note;
import by.bsuir.discussion.repository.NoteRepository;
import by.bsuir.discussion.service.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private static final AtomicLong ids = new AtomicLong(1);

    public List<NoteResponseDto> getAll() {
        return noteMapper.toDto(noteRepository.findAll());
    }

    public void deleteById(Long id) {
        Note entity = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        noteRepository.delete(entity);
    }

    public NoteResponseDto create(NoteRequestDto dto, Locale locale) {
        Note newEntity = noteMapper.toEntity(dto);
        newEntity.getKey().setCountry(locale);
        newEntity.getKey().setId(ids.getAndIncrement());
        return noteMapper.toDto(noteRepository.save(newEntity));
    }

    public NoteResponseDto getById(Long id) {
        Note entity = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        return noteMapper.toDto(entity);
    }

    public NoteResponseDto update(NoteRequestDto dto) {
        Note entity = noteRepository
                .findByKeyId(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + dto.id() + " is not found"));
        final Note updated = noteMapper.partialUpdate(dto, entity);
        return noteMapper.toDto(noteRepository.save(updated));
    }
}
