package by.bsuir.publicator.service.sticker.impl;

import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.bean.Sticker;
import by.bsuir.publicator.dto.StickerRequestTo;
import by.bsuir.publicator.dto.StickerResponseTo;
import by.bsuir.publicator.exception.EntityNotFoundException;
import by.bsuir.publicator.repository.issue.IssueRepository;
import by.bsuir.publicator.repository.sticker.StickerRepository;
import by.bsuir.publicator.service.sticker.IStickerService;
import by.bsuir.publicator.util.converter.sticker.StickerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class StickerService implements IStickerService {
    private final StickerConverter stickerConverter;
    private final StickerRepository stickerRepository;
    private final IssueRepository issueRepository;
    private final String ENTITY_NAME = "sticker";

    @Autowired
    public StickerService(StickerConverter stickerConverter, StickerRepository stickerRepository, IssueRepository issueRepository) {
        this.stickerConverter = stickerConverter;
        this.stickerRepository = stickerRepository;
        this.issueRepository = issueRepository;
    }
    @Override
    public List<StickerResponseTo> getStickers() {
        List<Sticker> stickers = stickerRepository.findAll();
        return stickers.stream().map(stickerConverter::convertToResponse).toList();
    }

    @Override
    public StickerResponseTo getStickerById(BigInteger id) throws EntityNotFoundException {
        Optional<Sticker> sticker = stickerRepository.findById(id);
        if (sticker.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return stickerConverter.convertToResponse(sticker.get());
    }

    @Override
    public StickerResponseTo createSticker(StickerRequestTo sticker) {
        List<Issue> issues = new ArrayList<>();
        if (sticker.getIssueIds() != null) {
            issues = issueRepository.findAllById(sticker.getIssueIds());
        }
        Sticker entity = stickerConverter.convertToEntity(sticker, issues);
        Sticker savedSticker = stickerRepository.save(entity);
        return stickerConverter.convertToResponse(savedSticker);
    }

    @Override
    public StickerResponseTo updateSticker(StickerRequestTo sticker) throws EntityNotFoundException {
        Optional<Sticker> stickerEntity = stickerRepository.findById(sticker.getId());
        if (stickerEntity.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, sticker.getId());
        }
        List<Issue> issues = new ArrayList<>();
        if (sticker.getIssueIds() != null) {
            issues = issueRepository.findAllById(sticker.getIssueIds());
        }
        Sticker entity = stickerConverter.convertToEntity(sticker, issues);
        Sticker savedSticker = stickerRepository.save(entity);
        return stickerConverter.convertToResponse(savedSticker);
    }

    @Override
    public void deleteSticker(BigInteger id) throws EntityNotFoundException {
        if (stickerRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        stickerRepository.deleteById(id);
    }
}
