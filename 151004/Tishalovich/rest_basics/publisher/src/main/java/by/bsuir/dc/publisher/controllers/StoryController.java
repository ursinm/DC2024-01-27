package by.bsuir.dc.publisher.controllers;

import by.bsuir.dc.publisher.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.StoryResponseTo;
import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.controllers.common.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/storys")
public class StoryController extends AbstractController<StoryRequestTo, StoryResponseTo> {

    public StoryController(AbstractService<StoryRequestTo, StoryResponseTo> service) {
        super(service);
    }

}
