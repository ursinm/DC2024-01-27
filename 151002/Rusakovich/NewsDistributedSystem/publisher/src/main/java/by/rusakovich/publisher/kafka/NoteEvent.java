package by.rusakovich.publisher.kafka;

import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;

import java.util.List;


public record NoteEvent(Operation operation, NoteRequestTO request, List<NoteResponseTO> responses) {
    public NoteEvent(Operation operation) {
        this(operation, null, null);
    }
    public NoteEvent(Operation operation, NoteRequestTO noteRequestTO) {
        this(operation, noteRequestTO, null);
    }

    public enum Operation {
        FIND_ALL,
        FIND_BY_ID,
        CREATE,
        UPDATE,
        REMOVE_BY_ID
    }
}
