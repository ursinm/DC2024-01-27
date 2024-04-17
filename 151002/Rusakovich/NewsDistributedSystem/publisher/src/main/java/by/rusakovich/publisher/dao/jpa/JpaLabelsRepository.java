package by.rusakovich.publisher.dao.jpa;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaLabel;
import by.rusakovich.publisher.service.exception.NotFound;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public interface JpaLabelsRepository extends CrudRepository<JpaLabel, Long>, IEntityRepository<Long, JpaLabel> {
    @Override
    default Optional<JpaLabel> readById(Long id){
        return findById(id);
    }

    @Override
    default Optional<JpaLabel> create(JpaLabel entity){
        return Optional.of(save(entity));
    }

    @Override
    default Optional<JpaLabel> update(JpaLabel entity){
        if(existsById(entity.getId())){
            save(entity);
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    default Iterable<JpaLabel> readAll(){
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
