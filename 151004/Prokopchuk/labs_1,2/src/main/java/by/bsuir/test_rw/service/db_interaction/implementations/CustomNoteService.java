package by.bsuir.test_rw.service.db_interaction.implementations;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.entity.implementations.Note;
import by.bsuir.test_rw.repository.interfaces.NoteRepository;
import by.bsuir.test_rw.service.db_interaction.interfaces.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomNoteService implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Note findById(Long id) throws EntityNotFoundException {
        return noteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public void save(Note entity) {
        noteRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = noteRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            noteRepository.deleteById(id);
        }
    }

    @Override
    public void update(Note entity) {
        boolean wasUpdated = noteRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            noteRepository.save(entity);
        }
    }
}
