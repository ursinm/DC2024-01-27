package by.rusakovich.newsdistributedsystem.dao.jpa;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaNews;
import by.rusakovich.newsdistributedsystem.model.entity.impl.jpa.JpaNote;
import by.rusakovich.newsdistributedsystem.service.exception.NotFound;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public interface JpaNoteRepository extends CrudRepository<JpaNote, Long>, IEntityRepository<Long, JpaNote> {
    @Override
    default Optional<JpaNote> readById(Long id){
        return findById(id);
    }

    @Override
    default Optional<JpaNote> create(JpaNote entity){
        return Optional.of(save(entity));
    }

    @Override
    default Optional<JpaNote> update(JpaNote entity){
        if(existsById(entity.getId())){
            save(entity);
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    default Iterable<JpaNote> readAll(){
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
