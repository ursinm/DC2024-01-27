package by.bsuir.dc.publisher.controllers;

import by.bsuir.dc.publisher.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.LabelResponseTo;
import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.controllers.common.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/labels")
public class LabelController extends AbstractController<LabelRequestTo, LabelResponseTo> {

    public LabelController(AbstractService<LabelRequestTo, LabelResponseTo> service) {
        super(service);
    }

}
