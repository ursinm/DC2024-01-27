package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.kafka.dto.NewsNoteDto;
import by.bsuir.poit.dc.kafka.dto.UpdateNewsNoteDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.dao.NewsRepository;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.Note;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class NoteMapper {
    @Autowired
    private NewsRepository newsRepository;

    public abstract UpdateNewsNoteDto buildRequest(
	UpdateNoteDto dto,
	List<String> countries);

    public abstract NoteDto unwrapResponse(NewsNoteDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news",
	source = "newsId",
	qualifiedByName = "getNewsRef")
    public abstract Note toEntity(UpdateNoteDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news",
	source = "newsId",
	qualifiedByName = "getNewsRef")
    public abstract Note partialUpdate(
	@MappingTarget Note note,
	UpdateNoteDto dto);

    @Mapping(target = "newsId", source = "news.id")
    public abstract NoteDto toDto(Note note);

    @Named("getNewsRef")
    protected News getNewsRef(long newsId) {
	return newsRepository.getReferenceById(newsId);
    }
}