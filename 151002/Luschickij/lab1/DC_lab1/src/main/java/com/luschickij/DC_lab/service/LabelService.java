package com.luschickij.DC_lab.service;

import com.luschickij.DC_lab.dao.repository.LabelRepository;
import com.luschickij.DC_lab.model.entity.Label;
import com.luschickij.DC_lab.model.request.LabelRequestTo;
import com.luschickij.DC_lab.model.response.LabelResponseTo;
import com.luschickij.DC_lab.service.exceptions.ResourceNotFoundException;
import com.luschickij.DC_lab.service.exceptions.ResourceStateException;
import com.luschickij.DC_lab.service.mapper.LabelMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class LabelService implements IService<LabelRequestTo, LabelResponseTo> {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelResponseTo findById(Long id) {
        return labelRepository.findById(id).map(labelMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<LabelResponseTo> findAll() {
        return labelMapper.getListResponse(labelRepository.findAll());
    }

    @Override
    public LabelResponseTo create(LabelRequestTo request) {
        LabelResponseTo response = labelMapper.getResponse(labelRepository.save(labelMapper.getLabel(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @Override
    public LabelResponseTo update(LabelRequestTo request) {
        LabelResponseTo response = labelMapper.getResponse(labelRepository.save(labelMapper.getLabel(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @Override
    public void removeById(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(LabelService::removeException);

        labelRepository.delete(label);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 21, "Can't find label by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 22, "Can't create label");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 23, "Can't update label");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 24, "Can't remove label");
    }
}
