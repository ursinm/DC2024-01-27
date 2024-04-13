package services.tweetservice.serivces.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.Sticker;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.mapper.StickerListMapper;
import services.tweetservice.domain.mapper.StickerMapper;
import services.tweetservice.domain.request.StickerRequestTo;
import services.tweetservice.domain.response.StickerResponseTo;
import services.tweetservice.exceptions.NoSuchMessageException;
import services.tweetservice.exceptions.NoSuchStickerException;
import services.tweetservice.repositories.StickerRepository;
import services.tweetservice.serivces.StickerService;

import java.util.List;

@Service
@Transactional
@Validated
public class StickerServiceImpl implements StickerService {
    private final StickerRepository stickerRepository;
    private final StickerMapper stickerMapper;
    private final StickerListMapper stickerListMapper;

    @Autowired
    public StickerServiceImpl(StickerRepository stickerRepository, StickerMapper stickerMapper, StickerListMapper stickerListMapper) {
        this.stickerRepository = stickerRepository;
        this.stickerMapper = stickerMapper;
        this.stickerListMapper = stickerListMapper;
    }

    @Override
    @Validated(ValidationMarker.OnCreate.class)
    public StickerResponseTo create(@Valid StickerRequestTo entity) {
        return stickerMapper.toStickerResponseTo(stickerRepository.save(stickerMapper.toSticker(entity)));
    }

    @Override
    public List<StickerResponseTo> read() {
        return stickerListMapper.toStickerResponseToList(stickerRepository.findAll());
    }

    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public StickerResponseTo update(@Valid StickerRequestTo entity) {
        if (stickerRepository.existsById(entity.id())) {
            Sticker sticker = stickerMapper.toSticker(entity);
            Sticker stickerRef = stickerRepository.getReferenceById(sticker.getId());
            sticker.setTweetStickerList(stickerRef.getTweetStickerList());
            return stickerMapper.toStickerResponseTo(stickerRepository.save(sticker));
        } else {
            throw new NoSuchMessageException(entity.id());
        }
    }

    @Override
    public void delete(Long id) {
        if (stickerRepository.existsById(id)) {
            stickerRepository.deleteById(id);
        } else {
            throw new NoSuchMessageException(id);
        }
    }

    @Override
    public StickerResponseTo findStickerById(Long id) {
        Sticker sticker = stickerRepository.findById(id).orElseThrow(() -> new NoSuchStickerException(id));
        return stickerMapper.toStickerResponseTo(sticker);
    }
}
