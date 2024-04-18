package by.rusakovich.discussion.kafka;


import by.rusakovich.discussion.model.dto.NoteRequestTO;
import by.rusakovich.discussion.model.dto.NoteResponseTO;

import java.util.List;


public record NoteEvent(Operation operation, Long id, NoteRequestTO request, List<NoteResponseTO> responses) {
    public NoteEvent(Operation operation) {
        this(operation, null, null, null);
    }
    public NoteEvent(List<NoteResponseTO> responses) {
        this(null, null, null, responses);
    }
    public NoteEvent(Operation operation, NoteRequestTO noteRequestTO) {
        this(operation, null, noteRequestTO, null);
    }
    public NoteEvent(Operation operation, Long id) {
        this(operation, id, null, null);
    }

    public enum Operation {
        FIND_ALL,
        FIND_BY_ID,
        CREATE,
        UPDATE,
        REMOVE_BY_ID
    }
}
