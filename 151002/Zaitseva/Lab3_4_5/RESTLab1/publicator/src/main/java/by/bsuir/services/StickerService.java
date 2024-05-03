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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@CacheConfig(cacheNames = "stickerCache")
public class StickerService {
    @Autowired
    StickerMapper stickerMapper;
    @Autowired
    StickerRepository stickerDao;
    @Autowired
    StickerListMapper stickerListMapper;
    @Cacheable(cacheNames = "stickers", key = "#id", unless = "#result == null")
    public StickerResponseTo getStickerById(@Min(0) Long id) throws NotFoundException {
        Optional<Sticker> sticker = stickerDao.findById(id);
        return sticker.map(value -> stickerMapper.stickerToStickerResponse(value)).orElseThrow(() -> new NotFoundException("Sticker not found!", 40004L));
    }
    @Cacheable(cacheNames = "stickers")
    public List<StickerResponseTo> getStickers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Sticker> stickers = stickerDao.findAll(pageable);
        return stickerListMapper.toStickerResponseList(stickers.toList());
    }
    @CacheEvict(cacheNames = "stickers", allEntries = true)
    public StickerResponseTo saveSticker(@Valid StickerRequestTo sticker) {
        Sticker stickerToSave = stickerMapper.stickerRequestToSticker(sticker);
        return stickerMapper.stickerToStickerResponse(stickerDao.save(stickerToSave));
    }
    @Caching(evict = { @CacheEvict(cacheNames = "stickers", key = "#id"),
            @CacheEvict(cacheNames = "stickers", allEntries = true) })

    public void deleteSticker(@Min(0) Long id) throws DeleteException {
        if (!stickerDao.existsById(id)) {
            throw new DeleteException("Sticker not found!", 40004L);
        } else {
            stickerDao.deleteById(id);
        }
    }
    @CacheEvict(cacheNames = "stickers", allEntries = true)
    public StickerResponseTo updateSticker(@Valid StickerRequestTo sticker) throws UpdateException {
        Sticker stickerToUpdate = stickerMapper.stickerRequestToSticker(sticker);
        if (!stickerDao.existsById(stickerToUpdate.getId())) {
            throw new UpdateException("Sticker not found!", 40004L);
        } else {
            return stickerMapper.stickerToStickerResponse(stickerDao.save(stickerToUpdate));
        }
    }

    public List<StickerResponseTo> getStickerByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Sticker> sticker = stickerDao.findStickersByIssueId(issueId);
        return stickerListMapper.toStickerResponseList(sticker);
    }
}
