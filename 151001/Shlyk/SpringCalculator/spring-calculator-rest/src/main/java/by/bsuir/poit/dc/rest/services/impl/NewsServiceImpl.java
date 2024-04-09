package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.context.CatchLevel;
import by.bsuir.poit.dc.context.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.LabelMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.NewsLabelMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.NewsMapper;
import by.bsuir.poit.dc.rest.api.dto.mappers.NoteMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsDto;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNewsLabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.NewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.PresenceDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceBusyException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.NewsLabelRepository;
import by.bsuir.poit.dc.rest.dao.NewsRepository;
import by.bsuir.poit.dc.rest.dao.UserRepository;
import by.bsuir.poit.dc.rest.model.News;
import by.bsuir.poit.dc.rest.model.NewsLabel;
import by.bsuir.poit.dc.rest.model.NewsLabelId;
import by.bsuir.poit.dc.rest.services.NewsService;
import com.google.errorprone.annotations.Keep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Slf4j
@Service
@CatchLevel(DataAccessException.class)
@CacheConfig(cacheNames = "newsCache")
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    //    private final LabelRepository labelRepository;
    private final UserRepository userRepository;
    private final NewsLabelRepository newsLabelRepository;
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;
    private final NoteMapper noteMapper;
    private final LabelMapper labelMapper;
    private final NewsLabelMapper newsLabelMapper;

    @Override
    @CatchThrows(call = "newNewsCreationException")
    public NewsDto create(UpdateNewsDto dto) {
	News entity = newsMapper.toEntity(dto);
	News savedEntity = newsRepository.save(entity);
	return newsMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newNewsModifyingException",
	args = "newsId")
    @CacheEvict(key = "#newsId")
    public NewsDto update(long newsId, UpdateNewsDto dto) {
	News entity = newsRepository
			  .findById(newsId)
			  .orElseThrow(() -> newNewsNotFoundException(newsId));
	News updatedEntity = newsMapper.partialUpdate(entity, dto);
	News savedEntity = newsRepository.save(updatedEntity);
	return newsMapper.toDto(savedEntity);
    }

    @Override
    @Cacheable
    public NewsDto getById(long newsId) {
	return newsRepository
		   .findById(newsId)
		   .map(newsMapper::toDto)
		   .orElseThrow(() -> newNewsNotFoundException(newsId));
    }

    @Override
    public List<NewsDto> getByOffsetAndLimit(long offset, int limit) {
	int number = (int) (offset / limit);
	PageRequest pageRequest = PageRequest.of(number, limit);
	Page<News> pages = newsRepository.findAll(pageRequest);
	return pages.stream()
		   .map(newsMapper::toDto)
		   .toList();
    }

    @Override
    public List<NewsDto> getAll() {
	return newsRepository.findAll().stream()
		   .map(newsMapper::toDto)
		   .toList();
    }

    @Override
    public PresenceDto existsById(long newsId) {
	return PresenceDto.wrap(newsRepository.existsById(newsId));
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newNewsModifyingException",
	args = "newsId")
    public PresenceDto delete(long newsId) {
	return PresenceDto
		   .wrap(newsRepository.existsById(newsId))
		   .ifPresent(() -> newsRepository.deleteById(newsId));
    }


    @Override
    @Transactional
    @CatchThrows(
	call = "newNewsModifyingException",
	args = "newsId")
    public void attachLabelById(long newsId, UpdateNewsLabelDto dto) {
	if (!newsRepository.existsById(newsId)) {
	    throw newNewsNotFoundException(newsId);
	}
	NewsLabel entity = newsLabelMapper.toEntity(newsId, dto);
	if (newsLabelRepository.existsById(entity.getId())) {
	    throw newLabelNewsAlreadyPresent(entity.getId());
	}
	NewsLabel _ = newsLabelRepository.save(entity);
    }

    @Override
    @Transactional
//    @CacheEvict(key = "#n")
    public PresenceDto detachLabelById(long newsId, long labelId) {
	return PresenceDto
		   .wrap(newsLabelRepository.existsByNewsIdAndLabelId(newsId, labelId))
		   .ifPresent(() -> newsLabelRepository.deleteByNewsIdAndLabelId(newsId, labelId));
    }

    @Override
    @Transactional
    @Cacheable
    public List<NewsDto> getNewsByUserId(long userId) {
	if (!userRepository.existsById(userId)) {
	    throw newUserNotFoundException(userId);
	}
	return newsMapper.toDtoList(
	    newsRepository.findAllByUserId(userId)
	);
    }

    @Override
    @Cacheable
    public List<NewsDto> getNewsByLabel(String label) {
	List<NewsLabel> news = newsLabelRepository.findAllByLabelName(label);
	return news.stream()
		   .map(NewsLabel::getNews)
		   .map(newsMapper::toDto)
		   .toList();
    }

    @Override
    @Cacheable(cacheNames = "labelCache", key = "#newsId")
    public List<LabelDto> getLabelsByNewsId(long newsId) {
	News news = newsRepository
			.findWithLabelsById(newsId)
			.orElseThrow(() -> newNewsNotFoundException(newsId));
	return news.getLabels().stream()
		   .map(NewsLabel::getLabel)
		   .map(labelMapper::toDto)
		   .toList();
    }

    @Keep
    private static ResourceModifyingException newNewsCreationException(
	Throwable cause
    ) {
	final String msg = STR."Failed to create news by cause = \{cause.getMessage()}";
	final String frontMsg = "Failed to create news. Check that news' title should be unique";
	log.warn(msg);
	return new ResourceModifyingException(frontMsg, 72);
    }

    @Keep
    private static ResourceModifyingException newNewsModifyingException(
	long newsId,
	Throwable cause
    ) {
	final String msg = STR."Failed to update news by id = \{newsId} by cause = \{cause.getMessage()}";
	final String frontMsg = "Failed to change news content. Verify that dto doesn't violate restrictions";
	log.warn(msg);
	return new ResourceModifyingException(frontMsg, 73);

    }

    @Keep
    private static ResourceModifyingException newNoteCreationException(
	long newsId,
	Throwable cause
    ) {
	final String msg = STR."Failed to create new note for news entity with id = \{newsId} by cause = \{cause.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(msg, 71);
    }

    @Keep
    private static ResourceNotFoundException newNewsNotFoundException(long newsId) {
	final String msg = STR."Failed to find news by id = \{newsId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 44);
    }

    @Keep
    private static ResourceNotFoundException newLabelNotFoundException(long labelId) {
	final String msg = STR."Failed to find label by id = \{labelId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 55);

    }

    @Keep
    private static ResourceBusyException newLabelNewsAlreadyPresent(NewsLabelId id) {
	final String msg = STR."The news label is already present by newsId=\{id.getLabelId()} and labelId=\{id.getLabelId()}";
	log.warn(msg);
	return new ResourceBusyException(msg, 52);

    }

    @Keep
    private static ResourceNotFoundException newUserNotFoundException(long userId) {
	final String msg = STR."Failed to find news for user with id = \{userId}, because user is not exists";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 43);
    }
}
