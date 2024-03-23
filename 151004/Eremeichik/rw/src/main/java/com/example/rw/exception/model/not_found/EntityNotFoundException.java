package com.example.rw.exception.model.not_found;

public class EntityNotFoundException extends NotFoundException {

    public EntityNotFoundException(){
        super("Entity was not found");
    }

    public EntityNotFoundException(Object id){
        super(String.format("Entity with id %s was not found", id.toString()));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
