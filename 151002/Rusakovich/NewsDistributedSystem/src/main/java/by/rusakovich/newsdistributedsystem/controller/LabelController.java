package by.rusakovich.newsdistributedsystem.controller;

import by.rusakovich.newsdistributedsystem.model.dto.label.LabelRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.label.LabelResponseTO;
import by.rusakovich.newsdistributedsystem.service.impl.LabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url}/labels")
public class LabelController extends Controller<Long, LabelRequestTO, LabelResponseTO, LabelService> {

    public LabelController(LabelService service) {
        super(service);
    }
}
