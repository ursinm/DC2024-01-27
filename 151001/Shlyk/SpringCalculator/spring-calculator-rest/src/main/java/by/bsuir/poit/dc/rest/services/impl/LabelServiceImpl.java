package by.bsuir.poit.dc.rest.services.impl;

import by.bsuir.poit.dc.rest.CatchThrows;
import by.bsuir.poit.dc.rest.api.dto.mappers.LabelMapper;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateLabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceModifyingException;
import by.bsuir.poit.dc.rest.api.exceptions.ResourceNotFoundException;
import by.bsuir.poit.dc.rest.dao.LabelRepository;
import by.bsuir.poit.dc.rest.model.Label;
import by.bsuir.poit.dc.rest.services.LabelService;
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
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelDto create(UpdateLabelDto dto) {
	Label entity = labelMapper.toEntity(dto);
	Label savedEntity = labelRepository.save(entity);
	return labelMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CatchThrows(
	call = "newLabelModifyingException",
	args = "labelId")
    public LabelDto update(long labelId, UpdateLabelDto dto) {
	Label entity = labelRepository
			   .findById(labelId)
			   .orElseThrow(() -> newLabelNotFountException(labelId));
	Label updatedEntity = labelMapper.partialUpdate(entity, dto);
	Label savedEntity = labelRepository.save(updatedEntity);
	return labelMapper.toDto(savedEntity);
    }

    @Override
    public LabelDto getById(long labelId) {
	return labelRepository
		   .findById(labelId)
		   .map(labelMapper::toDto)
		   .orElseThrow(() -> newLabelNotFountException(labelId));
    }

    @Override
    public LabelDto getByName(String name) {
	return labelRepository
		   .findByName(name)
		   .map(labelMapper::toDto)
		   .orElseThrow(() -> newLabelNotFountException(name));
    }

    @Override
    public List<LabelDto> getAll() {
	return labelMapper.toDtoList(
	    labelRepository.findAll()
	);
    }

    @Override
    @Transactional
    public boolean delete(long labelId) {
	boolean isDeleted;
	if (labelRepository.existsById(labelId)) {
	    labelRepository.deleteById(labelId);
	    isDeleted = true;
	} else {
	    isDeleted = false;
	}
	return isDeleted;
    }

    private static ResourceModifyingException newLabelModifyingException(
	long labelId,
	Throwable e) {
	final String frontMessage = STR."Failed to modify label by id=\{labelId}";
	final String msg = STR."Failed to modify label by id=\{labelId} with cause=\{e.getMessage()}";
	log.warn(msg);
	return new ResourceModifyingException(frontMessage, 50);
    }

    private static ResourceNotFoundException newLabelNotFountException(String name) {
	final String msg = STR."Failed to find label by name = \{name}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 47);

    }

    private static ResourceNotFoundException newLabelNotFountException(long labelId) {
	final String msg = STR."Failed to find label by id = \{labelId}";
	log.warn(msg);
	return new ResourceNotFoundException(msg, 48);
    }
}
