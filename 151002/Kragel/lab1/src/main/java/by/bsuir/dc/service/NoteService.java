package by.bsuir.dc.service;

import by.bsuir.dc.dto.request.NoteRequestDto;
import by.bsuir.dc.dto.response.NoteResponseDto;
import by.bsuir.dc.entity.Note;
import by.bsuir.dc.exception.ResourceNotFoundException;
import by.bsuir.dc.repository.NoteRepository;
import by.bsuir.dc.service.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public List<NoteResponseDto> getAll() {
        return noteMapper.toDto(noteRepository.getAll());
    }

    public void deleteById(Long id) {
        Note entity = noteRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        noteRepository.delete(entity);
    }

    public NoteResponseDto create(NoteRequestDto dto) {
        Note newEntity = noteMapper.toEntity(dto);
        return noteMapper.toDto(noteRepository.save(newEntity));
    }

    public NoteResponseDto getById(Long id) {
        Note entity = noteRepository
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + id + " is not found"));
        return noteMapper.toDto(entity);
    }

    public NoteResponseDto update(NoteRequestDto dto) {
        Note entity = noteRepository
                .getById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Note with id = " + dto.id() + " is not found"));
        if (dto.tweetId() != null)
            entity.setTweetId(dto.tweetId());
        if (dto.content() != null)
            entity.setContent(dto.content());
        return noteMapper.toDto(noteRepository.save(entity));
    }
}
