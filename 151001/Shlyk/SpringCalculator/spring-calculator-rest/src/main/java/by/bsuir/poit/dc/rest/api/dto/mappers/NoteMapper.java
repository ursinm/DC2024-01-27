package by.bsuir.poit.dc.rest.api.dto.mappers;

import by.bsuir.poit.dc.kafka.dto.*;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.dao.NewsRepository;
import by.bsuir.poit.dc.rest.model.News;
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

    public abstract KafkaUpdateNoteDto buildRequest(
	UpdateNoteDto dto,
	List<String> countries);

    public abstract NoteDto unwrapResponse(KafkaNoteDto dto);

    @Named("getNewsRef")
    protected News getNewsRef(long newsId) {
	return newsRepository.getReferenceById(newsId);
    }
}