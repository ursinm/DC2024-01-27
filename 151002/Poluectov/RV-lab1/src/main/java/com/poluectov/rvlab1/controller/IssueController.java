package com.poluectov.rvlab1.controller;

import com.poluectov.rvlab1.dto.issue.IssueRequestTo;
import com.poluectov.rvlab1.dto.issue.IssueResponseTo;
import com.poluectov.rvlab1.model.Issue;
import com.poluectov.rvlab1.service.CommonRestService;
import com.poluectov.rvlab1.utils.modelassembler.IssueModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues")
public class IssueController extends CommonRESTController<Issue, IssueRequestTo, IssueResponseTo> {


    public IssueController(CommonRestService<Issue, IssueRequestTo, IssueResponseTo> service,
                           IssueModelAssembler assembler) {
        super(service, assembler::toModel);
    }
}
