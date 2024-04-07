package by.rusakovich.newsdistributedsystem.service.impl;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteResponseTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Note;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaNote;
import by.rusakovich.newsdistributedsystem.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends EntityService<Long, NoteRequestTO, NoteResponseTO, JpaNote> {
    public NoteService(EntityMapper<Long, JpaNote, NoteRequestTO, NoteResponseTO> mapper, IEntityRepository<Long, JpaNote> rep) {
        super(mapper, rep);
    }
}
