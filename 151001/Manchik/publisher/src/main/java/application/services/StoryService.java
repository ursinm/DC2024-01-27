package application.services;

import application.dto.StoryRequestTo;
import application.dto.StoryResponseTo;
import application.exceptions.NotFoundException;

import java.util.List;

public interface StoryService extends CrudService<StoryRequestTo, StoryResponseTo>{
    List<StoryResponseTo> getByData(List<String> markerName, List<Long> markerId, String authorLogin, String title, String content) throws NotFoundException;
}
