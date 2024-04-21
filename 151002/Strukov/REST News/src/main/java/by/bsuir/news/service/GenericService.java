package by.bsuir.news.service;

import by.bsuir.news.dto.response.IResponseTO;
import by.bsuir.news.entity.IEntity;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.IMemoRepository;
import by.bsuir.news.repository.implementations.GenericRepositoryImplementation;

import java.util.ArrayList;
import java.util.List;

public class GenericService<T extends IEntity, R extends IResponseTO> {

    private IMemoRepository<T> repository = new GenericRepositoryImplementation<T>();
    private R instance;
    public GenericService(R instance) {
        this.instance = instance;
    }
    public R create(T entity) {
        if(repository.save(entity) != null) {
            return (R) instance.toModel(entity);
        }
        else {
            return null;
        }
    }

    public List<R> getAll() {
        List<R> r = new ArrayList<>();
        for(T t : repository.findAll()) {
            r.add((R)instance.toModel(t));
        }
        return r;
    }

    public R getById(Long id) {
        return (R)instance.toModel(repository.findById(id));
    }

    public R update(T entity) throws ClientException {
        if(repository.update(entity.getId(), entity) != null) {
            return (R) instance.toModel(entity);
        }
        throw new ClientException("Object not found!");
    }

    public Long delete(Long id) throws ClientException {
        if(repository.delete(id) != null) {
            return id;
        }
        throw new ClientException("Object not found!");
    }

}
