package by.bsuir.lab2.service;

import by.bsuir.lab2.dto.request.NoteRequestDto;
import by.bsuir.lab2.dto.response.NoteResponseDto;
import by.bsuir.lab2.entity.Note;
import by.bsuir.lab2.exception.ResourceNotFoundException;
import by.bsuir.lab2.repository.NoteRepository;
import by.bsuir.lab2.service.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Transactional(readOnly = true)
    public List<NoteResponseDto> getAll() {
        return noteMapper.toDto(noteRepository.findAll());
    }

    public void deleteById(Long id) {
        Note entity = noteRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        noteRepository.delete(entity);
    }

    public NoteResponseDto create(NoteRequestDto dto) {
        Note newEntity = noteMapper.toEntity(dto);
        return noteMapper.toDto(noteRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    public NoteResponseDto getById(Long id) {
        Note entity = noteRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        return noteMapper.toDto(entity);
    }

    public NoteResponseDto update(NoteRequestDto dto) {
        Note entity = noteRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + dto.id() + " is not found"));
        final Note updated = noteMapper.partialUpdate(dto, entity);
        return noteMapper.toDto(noteRepository.save(updated));
    }
}
