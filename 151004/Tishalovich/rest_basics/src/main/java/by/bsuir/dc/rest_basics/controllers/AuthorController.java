package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.controllers.common.AbstractController;
import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.services.common.AbstractService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/authors")
public class AuthorController extends AbstractController<AuthorRequestTo, AuthorResponseTo> {

    public AuthorController(AbstractService<AuthorRequestTo, AuthorResponseTo> service) {
        super(service);
    }

}
