package by.bsuir.dc.publisher.services.impl.mappers;

public interface EntityMapper<I, E, M> {

    M requestToModel(I requestTo);

    E modelToResponse(M model);

}
