package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.rest.api.dto.mappers.LabelMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.NewsLabelMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.NewsMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsLabelDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.NewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.*;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.NewsLabel;
import by.bsuir.poit.dc.rest.model.NewsLabelId;
import by.bsuir.poit.dc.rest.model.Note;
import by.bsuir.poit.dc.rest.services.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {
    //    private final LabelRepository labelRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NewsLabelRepository newsLabelRepository;
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;
    private final NoteMapper noteMapper;
    private final LabelMapper labelMapper;
    private final NewsLabelMapper newsLabelMapper;

    @Override
    public NewsDto create(UpdateNewsDto dto) {
	News entity = newsMapper.toEntity(dto);
	News savedEntity = newsRepository.save(entity);
	return newsMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public NewsDto update(long newsId, UpdateNewsDto dto) {
	News entity = newsRepository
			  .findById(newsId)
			  .orElseThrow(() -> newNewsNotFoundException(newsId));
	News updatedEntity = newsMapper.partialUpdate(entity, dto);
	News savedEntity = newsRepository.save(updatedEntity);
	return newsMapper.toDto(savedEntity);
    }

    @Override
    public NewsDto getById(long newsId) {
	return newsRepository
		   .findById(newsId)
		   .map(newsMapper::toDto)
		   .orElseThrow(() -> newNewsNotFoundException(newsId));
    }

    @Override
    public List<NewsDto> getAll() {
	return newsRepository.findAll().stream()
		   .map(newsMapper::toDto)
		   .toList();
    }

    @Override
    @Transactional
    public boolean delete(long newsId) {
	boolean isDeleted;
	if (newsRepository.existsById(newsId)) {
	    newsRepository.deleteById(newsId);
	    isDeleted = true;
	} else {
	    isDeleted = false;
	}
	return isDeleted;
    }

    @Override
    public NoteDto createNote(long newsId, UpdateNoteDto dto) {
	if (!newsRepository.existsById(newsId)) {
	    throw newNewsNotFoundException(newsId);
	}
	Note noteEntity = noteMapper.toEntity(dto, newsId);
	Note savedNote = noteRepository.save(noteEntity);
	return noteMapper.toDto(savedNote);
    }

    @Override
    @Transactional
    public void attachLabelById(long newsId, UpdateNewsLabelDto dto) {
	if (!newsRepository.existsById(newsId)) {
	    throw newNewsNotFoundException(newsId);
	}
	NewsLabel entity = newsLabelMapper.toEntity(newsId, dto);
//	if (!labelRepository.existsById(entity.getId().getLabelId())) {
//	    throw newLabelNotFoundException(entity.getLabel().getId());
//	}
	//this entity already holds id
	if (newsLabelRepository.existsById(entity.getId())) {
	    throw newLabelNewsAlreadyPresent(entity.getId());
	}
	NewsLabel _ = newsLabelRepository.save(entity);
    }

    @Override
    @Transactional
    public boolean detachLabelById(long newsId, long labelId) {
	boolean isDeleted;
	if (newsLabelRepository.existsByNewsIdAndLabelId(newsId, labelId)) {
	    newsLabelRepository.deleteByNewsIdAndLabelId(newsId, labelId);
	    isDeleted = true;
	} else {
	    isDeleted = false;
	}
	return isDeleted;
    }

    @Override
    @Transactional
    public List<NewsDto> getNewsByUserId(long userId) {
	if (!userRepository.existsById(userId)) {
	    throw newUserNotFoundException(userId);
	}
	return newsMapper.toDtoList(
	    newsRepository.findAllByAuthorId(userId)
	);
    }

    @Override
    public List<NewsDto> getNewsByLabel(String label) {
	List<NewsLabel> news = newsLabelRepository.findAllByLabelName(label);
	return news.stream()
		   .map(NewsLabel::getNews)
		   .map(newsMapper::toDto)
		   .toList();
    }

    @Override
    public List<NoteDto> getNotesByNewsId(long newsId) {
	News news = newsRepository
			.findWithNotesById(newsId)
			.orElseThrow(() -> newNewsNotFoundException(newsId));
	return news.getNotes().stream()
		   .map(noteMapper::toDto)
		   .toList();
    }

    @Override
    public List<LabelDto> getLabelsByNewsId(long newsId) {
	News news = newsRepository
			.findWithLabelsById(newsId)
			.orElseThrow(() -> newNewsNotFoundException(newsId));
	return news.getLabels().stream()
		   .map(NewsLabel::getLabel)
		   .map(labelMapper::toDto)
		   .toList();
    }

    private static ResourceNotFoundException newNewsNotFoundException(long newsId) {
	final String msg = STR."Failed to find news by id = \{newsId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 44);
    }
    @SuppressWarnings("unused")
    private static ResourceNotFoundException newLabelNotFoundException(long labelId) {
	final String msg = STR."Failed to find label by id = \{labelId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 55);

    }

    private static ResourceBusyException newLabelNewsAlreadyPresent(NewsLabelId id) {
	final String msg = STR."The news label is already present by newsId=\{id.getLabelId()} and labelId=\{id.getLabelId()}";
	log.warn(msg);
	return new ResourceBusyException(msg, 52);

    }

    private static ResourceNotFoundException newUserNotFoundException(long userId) {
	final String msg = STR."Failed to find news for user with id = \{userId}, because user is not exists";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 43);
    }
}
