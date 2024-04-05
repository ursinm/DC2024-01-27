package by.rusakovich.newsdistributedsystem.service.impl;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteResponseTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Note;
import by.rusakovich.newsdistributedsystem.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends EntityService<Long, NoteRequestTO, NoteResponseTO, Note<Long>> {
    public NoteService(EntityMapper<Long, Note<Long>, NoteRequestTO, NoteResponseTO> mapper, IEntityRepository<Long, Note<Long>> rep) {
        super(mapper, rep);
    }
}
