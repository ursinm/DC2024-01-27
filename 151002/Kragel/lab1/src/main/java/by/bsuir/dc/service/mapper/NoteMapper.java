package by.bsuir.dc.service.mapper;

import by.bsuir.dc.dto.request.NoteRequestDto;
import by.bsuir.dc.dto.response.NoteResponseDto;
import by.bsuir.dc.entity.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponseDto toDto(Note entity);

    Note toEntity(NoteRequestDto noteRequestDto);

    List<NoteResponseDto> toDto(Iterable<Note> entities);

    List<Note> toEntity(Iterable<NoteRequestDto> entities);
}
