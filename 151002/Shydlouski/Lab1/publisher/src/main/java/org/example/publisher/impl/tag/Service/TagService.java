package org.example.publisher.impl.tag.Service;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.note.Note;
import org.example.publisher.impl.tag.Tag;
import org.example.publisher.impl.tag.TagRepository;
import org.example.publisher.impl.tag.dto.TagRequestTo;
import org.example.publisher.impl.tag.dto.TagResponseTo;
import org.example.publisher.impl.tag.mapper.Impl.TagMapperImpl;
import org.example.publisher.impl.tweet.Tweet;
import org.example.publisher.impl.tweet.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TweetRepository tweetRepository;

    private final TagMapperImpl tagMapper;
    private final String ENTITY_NAME = "tag";

    public List<TagResponseTo> getTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponseTo> tagsTos = new ArrayList<>();
        for (var tag: tags) {
            tagsTos.add(tagMapper.tagToResponseTo(tag));
        }
        return tagsTos;
    }


    public TagResponseTo getTagById(BigInteger id) throws EntityNotFoundException {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return tagMapper.tagToResponseTo(tag.get());
    }


    public TagResponseTo saveTag(TagRequestTo tag) throws DuplicateEntityException {
        List<Tweet> tweets = new ArrayList<>();
        if (tag.getTweetIds() != null) {
            tweets = tweetRepository.findAllById(tag.getTweetIds());
        }
        Tag entity = tagMapper.dtoToEntity(tag, tweets);
        try {
            Tag savedTag = tagRepository.save(entity);
            return tagMapper.tagToResponseTo(savedTag);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }

    }

    public TagResponseTo updateTag(TagRequestTo tagRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        Optional<Tag> stickerEntity = tagRepository.findById(tagRequestTo.getId());
        if (stickerEntity.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, tagRequestTo.getId());
        }
        List<Tweet> tweets = new ArrayList<>();
        if (tagRequestTo.getTweetIds() != null) {
            tweets = tweetRepository.findAllById(tagRequestTo.getTweetIds());
        }
        try {
            Tag tag = tagRepository.save(tagMapper.dtoToEntity(tagRequestTo, tweets));
            return tagMapper.tagToResponseTo(tag);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEntityException(ENTITY_NAME, "");
        }
    }

    public void deleteTagById(BigInteger id) throws EntityNotFoundException {
        if (tagRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        tagRepository.deleteById(id);
    }

}
