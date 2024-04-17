package by.rusakovich.publisher.service.impl;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaNote;
import by.rusakovich.publisher.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends EntityService<Long, NoteRequestTO, NoteResponseTO, JpaNote> {
    public NoteService(EntityMapper<Long, JpaNote, NoteRequestTO, NoteResponseTO> mapper, IEntityRepository<Long, JpaNote> rep) {
        super(mapper, rep);
    }
}
