package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.dao.NewsRepository;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.Note;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

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