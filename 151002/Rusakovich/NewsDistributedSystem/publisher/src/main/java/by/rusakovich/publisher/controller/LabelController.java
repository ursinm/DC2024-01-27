package by.rusakovich.publisher.controller;

import by.rusakovich.publisher.model.dto.label.LabelRequestTO;
import by.rusakovich.publisher.model.dto.label.LabelResponseTO;
import by.rusakovich.publisher.service.impl.LabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url}/labels")
public class LabelController extends Controller<Long, LabelRequestTO, LabelResponseTO, LabelService> {

    public LabelController(LabelService service) {
        super(service);
    }
}
