package com.luschickij.publisher.controller.crud;

import com.luschickij.publisher.dto.creator.CreatorRequestTo;
import com.luschickij.publisher.dto.creator.CreatorResponseTo;
import com.luschickij.publisher.model.Creator;
import com.luschickij.publisher.service.CommonRestService;
import com.luschickij.publisher.utils.modelassembler.CreatorModelAssembler;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/creators")
public class CreatorController extends CommonRESTController<Creator, CreatorRequestTo, CreatorResponseTo> {


    public CreatorController(CommonRestService<Creator, CreatorRequestTo, CreatorResponseTo, Long> service, CreatorModelAssembler assembler) {
        super(service, assembler::toModel);
    }

}
