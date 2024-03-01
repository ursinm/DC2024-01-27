package by.bsuir.dc.rest_basics.services.common;

import java.util.List;

public interface AbstractService<I, E> {

    E create(I author);

    List<E> getAll();

    E get(Long id);

    E update(I author);

    E delete(Long id);

}
