package by.bsuir.rv.service.sticker.impl;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.exception.RepositoryException;
import by.bsuir.rv.repository.sticker.StickerRepositoryMemory;
import by.bsuir.rv.service.sticker.IStickerService;
import by.bsuir.rv.util.converter.sticker.StickerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;


@Component
public class StickerService implements IStickerService {
    private final StickerConverter stickerConverter;
    private final StickerRepositoryMemory stickerRepository;
    private final String ENTITY_NAME = "sticker";

    @Autowired
    public StickerService(StickerConverter stickerConverter, StickerRepositoryMemory stickerRepository) {
        this.stickerConverter = stickerConverter;
        this.stickerRepository = stickerRepository;
    }
    @Override
    public List<StickerResponseTo> getStickers() {
        List<Sticker> stickers = stickerRepository.findAll();
        return stickers.stream().map(stickerConverter::convertToResponse).toList();
    }

    @Override
    public StickerResponseTo getStickerById(BigInteger id) throws EntityNotFoundException {
        Sticker sticker;
        try {
            sticker = stickerRepository.findById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return stickerConverter.convertToResponse(sticker);
    }

    @Override
    public StickerResponseTo createSticker(StickerRequestTo sticker) {
        Sticker entity = stickerConverter.convertToEntity(sticker);
        Sticker savedSticker = stickerRepository.save(entity);
        return stickerConverter.convertToResponse(savedSticker);
    }

    @Override
    public StickerResponseTo updateSticker(StickerRequestTo sticker) throws EntityNotFoundException {
        try {
            stickerRepository.findById(sticker.getId());
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, sticker.getId());
        }
        Sticker entity = stickerConverter.convertToEntity(sticker);
        Sticker savedSticker = stickerRepository.save(entity);
        return stickerConverter.convertToResponse(savedSticker);
    }

    @Override
    public void deleteSticker(BigInteger id) throws EntityNotFoundException {
        try {
            stickerRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
    }
}
