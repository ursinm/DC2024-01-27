package by.haritonenko.rest.facade;

import by.haritonenko.rest.mapper.NoteMapper;
import by.haritonenko.rest.service.NoteService;
import by.haritonenko.rest.dto.request.CreateNoteDto;
import by.haritonenko.rest.dto.request.UpdateNoteDto;
import by.haritonenko.rest.dto.response.NoteResponseDto;
import by.haritonenko.rest.model.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteFacade {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteResponseDto findById(Long id) {
        Note note = noteService.findById(id);
        return noteMapper.toNoteResponse(note);
    }

    public List<NoteResponseDto> findAll() {
        List<Note> notes = noteService.findAll();

        return notes.stream().map(noteMapper::toNoteResponse).toList();
    }

    public NoteResponseDto save(CreateNoteDto noteRequest) {
        Note note = noteMapper.toNote(noteRequest);

        Note savedNote = noteService.save(note);

        return noteMapper.toNoteResponse(savedNote);
    }

    public NoteResponseDto update(UpdateNoteDto noteRequest) {
        Note note = noteService.findById(noteRequest.getId());

        Note updatedNote = noteMapper.toNote(noteRequest, note);

        return noteMapper.toNoteResponse(noteService.update(updatedNote));
    }

    public void delete(Long id) {
        noteService.deleteById(id);
    }
}
