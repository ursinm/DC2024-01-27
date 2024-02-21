package by.bsuir.dc.rest_basics.services.impl.mappers;

public interface EntityMapper<I, E, M> {

    M requestToModel(I requestTo);

    E modelToResponse(M model);

}
