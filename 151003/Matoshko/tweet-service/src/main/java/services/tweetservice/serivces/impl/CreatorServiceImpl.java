package services.tweetservice.serivces.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.Creator;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.mapper.CreatorListMapper;
import services.tweetservice.domain.mapper.CreatorMapper;
import services.tweetservice.domain.request.CreatorRequestTo;
import services.tweetservice.domain.response.CreatorResponseTo;
import services.tweetservice.exceptions.NoSuchCreatorException;
import services.tweetservice.repositories.CreatorRepository;
import services.tweetservice.serivces.CreatorService;

import java.util.List;

@Service
@Transactional
@Validated
public class CreatorServiceImpl implements CreatorService {
    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;
    private final CreatorListMapper creatorListMapper;

    @Autowired
    public CreatorServiceImpl(CreatorRepository creatorRepository, CreatorMapper creatorMapper, CreatorListMapper creatorListMapper) {
        this.creatorRepository = creatorRepository;
        this.creatorMapper = creatorMapper;
        this.creatorListMapper = creatorListMapper;
    }

    @Override
    @Validated(ValidationMarker.OnCreate.class)
    public CreatorResponseTo create(@Valid CreatorRequestTo entity) {
        return creatorMapper.toCreatorResponseTo(creatorRepository.save(creatorMapper.toCreator(entity)));
    }

    @Override
    public List<CreatorResponseTo> read() {
        return creatorListMapper.toCreatorResponseToList(creatorRepository.findAll());
    }

    // Можно сразу сделать проверку != и выкинуть исключение, но так более читабельно :)
    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public CreatorResponseTo update(@Valid CreatorRequestTo entity) {
        if (creatorRepository.existsById(entity.id())) {
            Creator creator = creatorMapper.toCreator(entity);
            creator.setTweetlist(creatorRepository.getReferenceById(creator.getId()).getTweetlist());
            return creatorMapper.toCreatorResponseTo(creatorRepository.save(creator));
        } else {
            throw new NoSuchCreatorException(entity.id());
        }

    }

    @Override
    public void delete(Long id) {
        if (creatorRepository.existsById(id)) {
            creatorRepository.deleteById(id);
        } else {
            throw new NoSuchCreatorException(id);
        }
    }

    @Override
    public CreatorResponseTo findCreatorById(Long id) {
        Creator creator = creatorRepository.findCreatorById(id).orElseThrow(() -> new NoSuchCreatorException(id));
        return creatorMapper.toCreatorResponseTo(creator);
    }
}
