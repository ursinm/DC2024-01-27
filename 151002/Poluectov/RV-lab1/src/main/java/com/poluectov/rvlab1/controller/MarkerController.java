package com.poluectov.rvlab1.controller;

import com.poluectov.rvlab1.dto.marker.MarkerRequestTo;
import com.poluectov.rvlab1.dto.marker.MarkerResponseTo;
import com.poluectov.rvlab1.model.Marker;
import com.poluectov.rvlab1.service.MarkerService;
import com.poluectov.rvlab1.utils.modelassembler.MarkerModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/markers")
public class MarkerController extends CommonRESTController<Marker, MarkerRequestTo, MarkerResponseTo> {
    public MarkerController(MarkerService service, MarkerModelAssembler assembler) {
        super(service, assembler::toModel);
    }
}
