package com.poluectov.rvlab1.controller;

import com.poluectov.rvlab1.dto.user.UserRequestTo;
import com.poluectov.rvlab1.dto.user.UserResponseTo;
import com.poluectov.rvlab1.model.User;
import com.poluectov.rvlab1.service.CommonRestService;
import com.poluectov.rvlab1.utils.modelassembler.UserModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends CommonRESTController<User, UserRequestTo, UserResponseTo> {


    public UserController(CommonRestService<User, UserRequestTo, UserResponseTo> service, UserModelAssembler assembler) {
        super(service, assembler::toModel);
    }

}
