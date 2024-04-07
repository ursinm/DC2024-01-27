package by.rusakovich.newsdistributedsystem.dao.jpa;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaAuthor;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaLabel;
import by.rusakovich.newsdistributedsystem.service.exception.NotFound;
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
