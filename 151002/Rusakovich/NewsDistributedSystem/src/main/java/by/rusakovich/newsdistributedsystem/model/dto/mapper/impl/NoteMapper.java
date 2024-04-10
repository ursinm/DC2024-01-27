package by.rusakovich.newsdistributedsystem.model.dto.mapper.impl;

import by.rusakovich.newsdistributedsystem.model.dto.mapper.ConversionError;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteResponseTO;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<Long, Note<Long>, NoteRequestTO, NoteResponseTO> {

    @Override
    Note<Long> mapToEntity(NoteRequestTO request)throws ConversionError;
    @Override
    List<NoteResponseTO> mapToResponseList(Iterable<Note<Long>> entities)throws ConversionError;
    @Override
    NoteResponseTO mapToResponse(Note<Long> entity)throws ConversionError;
}
