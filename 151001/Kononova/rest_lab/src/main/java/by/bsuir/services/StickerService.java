package by.bsuir.services;

import by.bsuir.dao.StickerDao;
import by.bsuir.dto.StickerRequestTo;
import by.bsuir.dto.StickerResponseTo;
import by.bsuir.entities.Sticker;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.StickerListMapper;
import by.bsuir.mapper.StickerMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class StickerService {

    @Autowired
    StickerMapper StickerMapper;
    @Autowired
    StickerDao stickerDao;
    @Autowired
    StickerListMapper StickerListMapper;

    public StickerResponseTo getStickerById(@Min(0) Long id) throws NotFoundException {
        Optional<Sticker> Sticker = stickerDao.findById(id);
        return Sticker.map(value -> StickerMapper.StickerToStickerResponse(value)).orElseThrow(() -> new NotFoundException("Sticker not found!", 40004L));
    }

    public List<StickerResponseTo> getStickers() {
        return StickerListMapper.toStickerResponseList(stickerDao.findAll());
    }

    public StickerResponseTo saveSticker(@Valid StickerRequestTo Sticker) {
        Sticker stickerToSave = StickerMapper.StickerRequestToSticker(Sticker);
        return StickerMapper.StickerToStickerResponse(stickerDao.save(stickerToSave));
    }

    public StickerResponseTo updateSticker(@Valid StickerRequestTo Sticker) throws UpdateException {
        Sticker stickerToUpdate = StickerMapper.StickerRequestToSticker(Sticker);
        return StickerMapper.StickerToStickerResponse(stickerDao.update(stickerToUpdate));
    }

    public StickerResponseTo getStickerByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Sticker> Sticker = stickerDao.getStickerByIssueId(issueId);
        return Sticker.map(value -> StickerMapper.StickerToStickerResponse(value)).orElseThrow(() -> new NotFoundException("Sticker not found!", 40004L));
    }

    public void deleteSticker(@Min(0) Long id) throws DeleteException {
        stickerDao.delete(id);
    }
}
