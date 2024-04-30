package by.bsuir.services;

import by.bsuir.dto.StickerRequestTo;
import by.bsuir.dto.StickerResponseTo;
import by.bsuir.entities.Sticker;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.StickerListMapper;
import by.bsuir.mapper.StickerMapper;
import by.bsuir.repository.StickerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    StickerRepository stickerDao;
    @Autowired
    StickerListMapper StickerListMapper;

    public StickerResponseTo getStickerById(@Min(0) Long id) throws NotFoundException {
        Optional<Sticker> Sticker = stickerDao.findById(id);
        return Sticker.map(value -> StickerMapper.StickerToStickerResponse(value)).orElseThrow(() -> new NotFoundException("Sticker not found!", 40004L));
    }

    public List<StickerResponseTo> getStickers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Sticker> stickers = stickerDao.findAll(pageable);
        return StickerListMapper.toStickerResponseList(stickers.toList());
    }

    public StickerResponseTo saveSticker(@Valid StickerRequestTo Sticker) {
        Sticker stickerToSave = StickerMapper.StickerRequestToSticker(Sticker);
        return StickerMapper.StickerToStickerResponse(stickerDao.save(stickerToSave));
    }

    public StickerResponseTo updateSticker(@Valid StickerRequestTo Sticker) throws UpdateException {
        Sticker stickerToUpdate = StickerMapper.StickerRequestToSticker(Sticker);
        if (!stickerDao.existsById(stickerToUpdate.getId())){
            throw new UpdateException("Sticker not found!", 40004L);
        } else {
            return StickerMapper.StickerToStickerResponse(stickerDao.save(stickerToUpdate));
        }
    }

    public List<StickerResponseTo> getStickerByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Sticker> sticker = null;
        return StickerListMapper.toStickerResponseList(sticker);
    }

    public void deleteSticker(@Min(0) Long id) throws DeleteException {
        if (!stickerDao.existsById(id)) {
            throw new DeleteException("Sticker not found!", 40004L);
        } else {
            stickerDao.deleteById(id);
        }
    }
}
