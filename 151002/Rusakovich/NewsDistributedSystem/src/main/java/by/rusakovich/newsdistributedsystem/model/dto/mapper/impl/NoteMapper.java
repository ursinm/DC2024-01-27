package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteResponseTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public interface NoteMapper extends EntityMapper<Long, Note<Long>, NoteRequestTO, NoteResponseTO> {

    Note<Long> mapToEntity(NoteRequestTO request);
    List<NoteResponseTO> mapToResponseList(Iterable<Note<Long>> entities);
    NoteResponseTO mapToResponse(Note<Long> entity);
}
