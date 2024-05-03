package com.luschickij.publisher.controller.crud;

import com.luschickij.publisher.dto.label.LabelRequestTo;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import com.luschickij.publisher.model.Label;
import com.luschickij.publisher.service.LabelService;
import com.luschickij.publisher.utils.modelassembler.LabelModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labels")
public class LabelController extends CommonRESTController<Label, LabelRequestTo, LabelResponseTo> {
    public LabelController(LabelService service, LabelModelAssembler assembler) {
        super(service, assembler::toModel);
    }
}
