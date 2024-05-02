package application.services;


import application.dto.MarkerRequestTo;
import application.dto.MarkerResponseTo;
import application.exceptions.NotFoundException;

import java.util.List;

public interface MarkerService extends CrudService<MarkerRequestTo, MarkerResponseTo>{
    List<MarkerResponseTo> getByStoryId(Long storyId) throws NotFoundException;
}
