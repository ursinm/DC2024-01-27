package by.bsuir.springapi.service;

import by.bsuir.springapi.dao.impl.StickerRepository;
import by.bsuir.springapi.model.request.StickerRequestTo;
import by.bsuir.springapi.model.response.StickerResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.StickerMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class StickerService implements RestService <StickerRequestTo, StickerResponseTo> {
    private final StickerRepository stickerRepository;

    private final StickerMapper stickerMapper;

    @Override
    public List<StickerResponseTo> findAll() {
        return stickerMapper.getListResponseTo(stickerRepository.getAll());
    }

    @Override
    public StickerResponseTo findById(Long id) {
        return stickerMapper.getResponseTo(stickerRepository
                .getBy(id)
                .orElseThrow(() -> tagNotFoundException(id)));
    }

    @Override
    public StickerResponseTo create(StickerRequestTo tagTo) {
        return stickerRepository
                .save(stickerMapper.getTag(tagTo))
                .map(stickerMapper::getResponseTo)
                .orElseThrow(StickerService::tagStateException);
    }

    @Override
    public StickerResponseTo update(StickerRequestTo tagTo) {
        stickerRepository
                .getBy(stickerMapper.getTag(tagTo).getId())
                .orElseThrow(() -> tagNotFoundException(stickerMapper.getTag(tagTo).getId()));
        return stickerRepository
                .update(stickerMapper.getTag(tagTo))
                .map(stickerMapper::getResponseTo)
                .orElseThrow(StickerService::tagStateException);
    }

    @Override
    public boolean removeById(Long id) {
        if (!stickerRepository.removeById(id)) {
            throw tagNotFoundException(id);
        }
        return true;
    }

    private static ResourceNotFoundException tagNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find tag with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 33);
    }

    private static ResourceStateException tagStateException() {
        return new ResourceStateException("Failed to create/update tag with specified credentials", HttpStatus.CONFLICT.value() * 100 + 34);
    }
}
