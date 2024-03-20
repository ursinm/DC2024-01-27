package by.rusakovich.newsdistributedsystem.model.entity;

public abstract class Entity implements IEntity<Long>{
    private Long id;
    @Override
    public Long getId(){return id;}

    @Override
    public void setId(Long newId) {
        id = newId;
    }
}
