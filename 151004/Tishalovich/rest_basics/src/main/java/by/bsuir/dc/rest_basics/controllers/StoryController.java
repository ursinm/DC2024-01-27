package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.controllers.common.AbstractController;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.StoryResponseTo;
import by.bsuir.dc.rest_basics.services.common.AbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/storys")
public class StoryController extends AbstractController<StoryRequestTo, StoryResponseTo> {

    public StoryController(AbstractService<StoryRequestTo, StoryResponseTo> service) {
        super(service);
    }

}
