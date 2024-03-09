package by.bsuir.poit.dc.cassandra.api.mappers;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.api.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.cassandra.model.Note;
import by.bsuir.poit.dc.cassandra.model.NoteByNews;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class
)
public abstract class NoteMapper {

    @Mapping(target = "id", source = "id")
    public abstract Note toEntity(long id, UpdateNoteDto dto);

    public abstract NoteByNews toNewsEntity(Note note);

    public abstract Note partialUpdate(
	@MappingTarget Note note,
	UpdateNoteDto dto);

    public abstract NoteByNews partialUpdate(
	@MappingTarget NoteByNews note,
	UpdateNoteDto dto
    );

    public abstract NoteDto toDto(Note note);

    public abstract NoteDto toDto(NoteByNews noteByNews);

    public abstract List<NoteDto> toDtoList(List<Note> list);

    public abstract List<NoteDto> toDtoListFromNoteByNews(List<NoteByNews> list);
}