package com.poluectov.rvlab1.controller;

import com.poluectov.rvlab1.dto.message.MessageRequestTo;
import com.poluectov.rvlab1.dto.message.MessageResponseTo;
import com.poluectov.rvlab1.model.Message;
import com.poluectov.rvlab1.service.CommonRestService;
import com.poluectov.rvlab1.utils.modelassembler.MessageModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/messages")
public class MessageController extends CommonRESTController<Message, MessageRequestTo, MessageResponseTo> {
    public MessageController(CommonRestService<Message, MessageRequestTo, MessageResponseTo> service,
                             MessageModelAssembler assembler) {
        super(service, assembler::toModel);
    }

}
