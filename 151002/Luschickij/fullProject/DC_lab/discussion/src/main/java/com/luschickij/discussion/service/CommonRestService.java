package com.luschickij.discussion.service;

import com.luschickij.discussion.dto.post.PostRequestTo;
import com.luschickij.discussion.dto.post.PostResponseTo;
import com.luschickij.discussion.model.Post;
import com.luschickij.discussion.repository.PostRepository;
import com.luschickij.discussion.repository.exception.EntityNotFoundException;
import com.luschickij.discussion.utils.dtoconverter.DtoConverter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Validated
public abstract class CommonRestService {
    PostRepository repository;
    DtoConverter<PostRequestTo, Post> dtoConverter;

    public CommonRestService(PostRepository repository,
                             DtoConverter<PostRequestTo, Post> dtoConverter) {
        this.repository = repository;
        this.dtoConverter = dtoConverter;
    }

    abstract Optional<PostResponseTo> mapResponseTo(Post entity);

    abstract void update(Post one, Post found);

    public List<PostResponseTo> all() {
        List<PostResponseTo> all = new ArrayList<>();
        for (Post one : repository.findAll()) {
            Optional<PostResponseTo> to = mapResponseTo(one);
            to.ifPresent(all::add);
        }
        return all;
    }

    public Optional<PostResponseTo> one(Long id) {


        List<Post> one = repository.findById(id);
        if (!one.isEmpty()) {
            return mapResponseTo(one.get(0));
        }
        return Optional.empty();
    }

    public Optional<PostResponseTo> create(@Valid PostRequestTo request) {
        Post one = dtoConverter.convert(request);
        Post entity = repository.save(one);
        return mapResponseTo(entity);
    }

    @Transactional
    public Optional<PostResponseTo> update(Long id, @Valid PostRequestTo request) {
        //repository updates by id
        Post newCreator = dtoConverter.convert(request);

        List<Post> found = repository.findById(id);

        if (found.isEmpty()) {
            return Optional.empty();
        }


        Post one = found.get(0);
        repository.deleteById(one.getCountry(), one.getNewsId(), one.getId());
        update(one, newCreator);

        Post entity = repository.save(one);
        return mapResponseTo(entity);
    }

    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        try {
            List<Post> toDeleteList = repository.findById(id);

            Post toDelete = toDeleteList.get(0);
            repository.deleteById(toDelete.getCountry(), toDelete.getNewsId(), toDelete.getId());
        } catch (Exception e) {
            throw new EntityNotFoundException(getEntityName() + " with id " + id + " not found");
        }
    }

    protected String getEntityName() {
        String className = this.getClass().getSimpleName();
        className = className.replace("Service", "");
        for (int i = className.length() - 1; i >= 0; i--) {
            if (Character.isUpperCase(className.charAt(i))) {
                className = className.substring(i);
                break;
            }
        }
        return className;
    }

}
