package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Issue;
import org.example.dc.model.IssueDto;
import org.example.dc.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresIssueService implements IssueService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<IssueDto> getIssues() {
        return jdbcTemplate.query("select * from tbl_issue", new BeanPropertyRowMapper<>(Issue.class)).stream()
                .map(issue -> converter.convert(issue)).collect(Collectors.toList());
    }

    @Override
    public IssueDto getIssueById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_issue where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Issue.class)).get(0));
    }

    @Override
    public IssueDto createIssue(IssueDto issueDto) {
        issueDto.setCreated(new Date(System.currentTimeMillis()));
        issueDto.setModified(new Date(System.currentTimeMillis()));
        Issue issue = converter.convert(issueDto);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_issue").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(issue));
        issueDto.setId(number.intValue());
        return issueDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        if(jdbcTemplate.query("select * from tbl_issue where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Issue.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_issue where id=?", new Object[]{id});
        return true;
    }

    @Override
    public IssueDto update(IssueDto issueDto) {
        issueDto.setModified(new Date(System.currentTimeMillis()));
        Issue issue = converter.convert(issueDto);
        jdbcTemplate.update("update tbl_issue set editorId=?, title=?, content=?," +
                "created=?, modified=? where id=?",
                new Object[]{issue.getEditorId(), issue.getTitle(), issue.getContent(), issue.getCreated(),
                        issue.getModified(), issue.getId()});
        return issueDto;
    }
}
