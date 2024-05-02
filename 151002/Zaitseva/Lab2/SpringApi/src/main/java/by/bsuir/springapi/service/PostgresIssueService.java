package by.bsuir.springapi.service;

import by.bsuir.springapi.model.entity.Creator;
import by.bsuir.springapi.model.entity.Issue;
import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.request.IssueRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import by.bsuir.springapi.model.response.IssueResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.CreatorMapper;
import by.bsuir.springapi.service.mapper.IssueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresIssueService  implements RestService<IssueRequestTo, IssueResponseTo>{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IssueMapper issueMapper;

    @Override
    public List<IssueResponseTo> findAll() {
        return jdbcTemplate.query("select * from issue", new BeanPropertyRowMapper<>(Issue.class)).stream()
                .map(issueMapper::getResponseTo).collect(Collectors.toList());
    }

    @Override
    public IssueResponseTo findById(Long id) {
        return jdbcTemplate.query("select * from issue where id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Issue.class)).stream()
                .map(issueMapper::getResponseTo)
                .findFirst()
                .orElseThrow(() -> issueNotFoundException(id));
    }

    @Override
    public IssueResponseTo create(IssueRequestTo creatorTo) {
        Issue issue = issueMapper.getNews(creatorTo);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = null;
        try {
            number = insert.withTableName("issue").usingGeneratedKeyColumns("id")
                    .executeAndReturnKey(new BeanPropertySqlParameterSource(issue));
        } catch (Exception e) {
            throw issueStateException();
        }
        issue.setId(number.longValue());
        return issueMapper.getResponseTo(issue);
    }

    @Override
    public IssueResponseTo update(IssueRequestTo creatorTo) {
        Issue issue = issueMapper.getNews(creatorTo);
        try {
            jdbcTemplate.update("update issue set creatorid=?, title=?, i_content=?, created=?, modified=? where id=?",
                    new Object[]{issue.getCreatorId(), issue.getTitle(), issue.getI_content(),
                            issue.getCreated(), issue.getModified(), issue.getId()});
        } catch (Exception e) {
            throw issueStateException();
        }
        return issueMapper.getResponseTo(issue);
    }

    @Override
    public boolean removeById(Long id) {
        if (jdbcTemplate.query("select * from issue where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Issue.class)).isEmpty()) {
            throw issueNotFoundException(id);
        }
        jdbcTemplate.update("delete from issue where id=?", new Object[]{id});
        return true;
    }

    private static ResourceNotFoundException issueNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find news with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 53);
    }

    private static ResourceStateException issueStateException() {
        return new ResourceStateException("Failed to create/update news with specified credentials", HttpStatus.CONFLICT.value() * 100 + 54);
    }
}
