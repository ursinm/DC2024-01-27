package by.bsuir.poit.dc.cassandra.api.mappers;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.api.mappers.config.CentralMapperConfig;
import by.bsuir.poit.dc.cassandra.model.NoteById;
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

    public abstract NoteById toEntityById(long id, UpdateNoteDto dto);

    public abstract NoteByNews toEntityByNews(long id, UpdateNoteDto dto);

    public abstract NoteById partialUpdate(
	@MappingTarget NoteById note,
	UpdateNoteDto dto);

    public abstract NoteByNews partialUpdate(
	@MappingTarget NoteByNews note,
	UpdateNoteDto dto
    );

    public abstract NoteDto toDto(NoteById note);

    public abstract NoteDto toDto(NoteByNews noteByNews);

    public abstract List<NoteDto> toDtoList(List<NoteById> list);

    public abstract List<NoteDto> toDtoListFromNoteByNews(List<NoteByNews> list);
}