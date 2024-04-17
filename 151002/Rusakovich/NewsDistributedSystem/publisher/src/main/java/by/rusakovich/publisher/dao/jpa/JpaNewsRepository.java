package by.rusakovich.publisher.dao.jpa;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaNews;
import by.rusakovich.publisher.service.exception.NotFound;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public interface JpaNewsRepository extends CrudRepository<JpaNews, Long>, IEntityRepository<Long, JpaNews> {

    @Override
    default Optional<JpaNews> readById(Long id){
        return findById(id);
    }

    @Override
    default Optional<JpaNews> create(JpaNews entity){
        return Optional.of(save(entity));
    }

    @Override
    default Optional<JpaNews> update(JpaNews entity){
        if(existsById(entity.getId())){
            save(entity);
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    default Iterable<JpaNews> readAll(){
        return findAll();
    }

    @Override
    default void removeById(Long id){
        if(existsById(id)){
            deleteById(id);
        }else{
            throw new NotFound(id);
        }
    }
}
