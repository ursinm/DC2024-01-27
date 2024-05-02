package org.example.dc.controllers;

import org.example.dc.model.IssueDto;
import org.example.dc.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1.0/issues")
public class IssueController {
    private Map<Integer, IssueDto> cache = new HashMap<>();

    @Autowired
    private IssueService issueService;
    @Cacheable
    @GetMapping
    public List<IssueDto> getIssues() {
        return issueService.getIssues();
    }

    @Cacheable
    @GetMapping("/{id}")
    public IssueDto getIssue(@PathVariable int id, HttpServletResponse response) {
        IssueDto issue = null;
        try {
            response.setStatus(200);
            issue = issueService.getIssueById(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
        cache.put(id, issue);
        return issue;
    }

    @PostMapping
    public IssueDto create(@RequestBody @Valid IssueDto issueDto, BindingResult br, HttpServletResponse response) {
        if (br.hasErrors()) {
            response.setStatus(400);
            return new IssueDto();
        }
        response.setStatus(201);
        try {
            return issueService.createIssue(issueDto);
        } catch (Exception e) {
            response.setStatus(403);
            return issueService.getIssues().stream()
                    .filter(i -> i.getTitle().equals(issueDto.getTitle()))
                    .findFirst().orElse(new IssueDto());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            issueService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    @PutMapping
    public IssueDto update(@RequestBody @Valid IssueDto issueDto, BindingResult br, HttpServletResponse response) {
        if (br.hasErrors()) {
           response.setStatus(402);
           return issueService.getIssueById(issueDto.getId());
        }
        return issueService.update(issueDto);
    }
}
