package by.bsuir.discussion.service;

import by.bsuir.discussion.dao.NoteRepository;
import by.bsuir.discussion.model.entity.Note;
import by.bsuir.discussion.model.entity.NoteState;
import by.bsuir.discussion.model.request.NoteRequestTo;
import by.bsuir.discussion.model.response.NoteResponseTo;
import by.bsuir.discussion.service.exceptions.ResourceNotFoundException;
import by.bsuir.discussion.service.mapper.NoteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class NoteService implements RestService<NoteRequestTo, NoteResponseTo> {
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

    private static Long getTimeBasedId() {
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }

    @Override
    public List<NoteResponseTo> findAll() {
        return noteMapper.getListResponseTo(noteRepository.findAll());
    }

    @Override
    public NoteResponseTo findById(Long id) {
        return noteMapper.getResponseTo(noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> noteNotFoundException(id)));
    }

    @Override
    public NoteResponseTo create(NoteRequestTo noteTo) {
        return create(noteTo, Locale.ENGLISH);
    }

    public NoteResponseTo create(@Valid NoteRequestTo noteTo, Locale locale) {
        Note newNote = noteMapper.getNote(noteTo);
        newNote.getKey().setCountry(locale);
        newNote.getKey().setId(getTimeBasedId());
        newNote.setState(NoteState.APPROVE);
        return noteMapper.getResponseTo(noteRepository.save(newNote));
    }

    @Override
    public NoteResponseTo update(@Valid NoteRequestTo noteTo) {
        Note note = noteRepository
                .findByKeyId(noteTo.id())
                .orElseThrow(() -> noteNotFoundException(noteTo.id()));
        return noteMapper.getResponseTo(noteRepository.save(noteMapper.partialUpdate(noteTo, note)));
    }

    @Override
    public void removeById(Long id) {
        Note note = noteRepository
                .findByKeyId(id)
                .orElseThrow(() -> noteNotFoundException(id));
        noteRepository.delete(note);
    }

    private static ResourceNotFoundException noteNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find note with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 83);
    }
}
