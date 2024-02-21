package by.bsuir.dc.rest_basics.controllers;

import by.bsuir.dc.rest_basics.controllers.common.AbstractController;
import by.bsuir.dc.rest_basics.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.LabelResponseTo;
import by.bsuir.dc.rest_basics.services.common.AbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/labels")
public class LabelController extends AbstractController<LabelRequestTo, LabelResponseTo> {

    public LabelController(AbstractService<LabelRequestTo, LabelResponseTo> service) {
        super(service);
    }

}
