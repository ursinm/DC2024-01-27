package by.haritonenko.jpa.facade;

import by.haritonenko.jpa.dto.request.CreateNoteDto;
import by.haritonenko.jpa.dto.request.UpdateNoteDto;
import by.haritonenko.jpa.dto.response.NoteResponseDto;
import by.haritonenko.jpa.mapper.NoteMapper;
import by.haritonenko.jpa.model.Note;
import by.haritonenko.jpa.model.Story;
import by.haritonenko.jpa.service.NoteService;
import by.haritonenko.jpa.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteFacade {

    private final NoteService noteService;
    private final StoryService storyService;
    private final NoteMapper noteMapper;

    @Transactional(readOnly = true)
    public NoteResponseDto findById(Long id) {
        Note note = noteService.findById(id);
        return noteMapper.toNoteResponse(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponseDto> findAll() {
        List<Note> notes = noteService.findAll();

        return notes.stream().map(noteMapper::toNoteResponse).toList();
    }

    @Transactional
    public NoteResponseDto save(CreateNoteDto noteRequest) {
        Note note = noteMapper.toNote(noteRequest);

        Story story = storyService.findById(noteRequest.getStoryId());
        note.setStory(story);

        Note savedNote = noteService.save(note);

        return noteMapper.toNoteResponse(savedNote);
    }

    @Transactional
    public NoteResponseDto update(UpdateNoteDto noteRequest) {
        Note note = noteService.findById(noteRequest.getId());

        Note updatedNote = noteMapper.toNote(noteRequest, note);

        if (noteRequest.getStoryId() != null) {
            Story story = storyService.findById(noteRequest.getStoryId());
            updatedNote.setStory(story);
        }

        return noteMapper.toNoteResponse(noteService.update(updatedNote));
    }

    @Transactional
    public void delete(Long id) {
        noteService.deleteById(id);
    }
}
