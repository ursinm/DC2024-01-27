package by.haritonenko.rest.service.impl;

import by.haritonenko.rest.exception.EntityNotFoundException;
import by.haritonenko.rest.repository.AbstractRepository;
import by.haritonenko.rest.model.Note;
import by.haritonenko.rest.service.NoteService;
import by.haritonenko.rest.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    public static final String NOTE_NOT_FOUND_MESSAGE = "Note with id '%d' doesn't exist";
    private final AbstractRepository<Note, Long> noteRepository;
    private final StoryService storyService;

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> {
            final String message = NOTE_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        noteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));

        noteRepository.deleteById(id);
    }

    @Override
    public Note save(Note note) {
        storyService.findById(note.getStoryId());
        return noteRepository.save(note);
    }

    @Override
    public Note update(Note note) {storyService.findById(note.getStoryId());
        noteRepository.findById(note.getId()).orElseThrow(()-> new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE));
        storyService.findById(note.getStoryId());

        return noteRepository.update(note);
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }
}

