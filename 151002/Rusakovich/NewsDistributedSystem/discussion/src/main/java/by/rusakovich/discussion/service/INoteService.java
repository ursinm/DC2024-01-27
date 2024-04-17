package by.rusakovich.discussion.service;

import by.rusakovich.discussion.model.dto.NoteRequestTO;
import by.rusakovich.discussion.model.dto.NoteResponseTO;

import java.util.List;


public interface INoteService {
    NoteResponseTO readById(Long id);
    List<NoteResponseTO> readAll();
    NoteResponseTO create(NoteRequestTO newEntity, String country);
    NoteResponseTO update(NoteRequestTO updatedEntity);
    void deleteById(Long id);
}
