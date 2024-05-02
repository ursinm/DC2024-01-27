package by.bsuir.springapi.service;

import by.bsuir.springapi.model.entity.Creator;
import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.CreatorMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class PostgresCreatorService implements RestService<CreatorRequestTo, CreatorResponseTo> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CreatorMapper creatorMapper;

    @Override
    public List<CreatorResponseTo> findAll() {
        return jdbcTemplate.query("select * from creator", new BeanPropertyRowMapper<>(Creator.class)).stream()
                .map(creatorMapper::getResponseTo).collect(Collectors.toList());
    }

    @Override
    public CreatorResponseTo findById(Long id) {
        return jdbcTemplate.query("select * from creator where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Creator.class)).stream()
                .map(creatorMapper::getResponseTo)
                .findFirst()
                .orElseThrow(() -> editorNotFoundException(id));
    }

    @Override
    public CreatorResponseTo create(CreatorRequestTo editorTo) {
        Creator creator = creatorMapper.getCreator(editorTo);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = null;
        try {
            number = insert.withTableName("creator").usingGeneratedKeyColumns("id")
                    .executeAndReturnKey(new BeanPropertySqlParameterSource(creator));
        } catch (Exception e) {
            throw editorStateException();
        }
        creator.setId(number.longValue());
        return creatorMapper.getResponseTo(creator);
    }

    @Override
    public CreatorResponseTo update(CreatorRequestTo editorTo) {
        Creator creator = creatorMapper.getCreator(editorTo);
        try {
            jdbcTemplate.update("update creator set c_login=?, c_password=?, firstname=?, lastname=? where id=?",
                    new Object[]{creator.getC_login(), creator.getC_password(), creator.getFirstName(),
                            creator.getLastName(), creator.getId()});
        } catch (Exception e) {
            throw editorStateException();
        }
        return creatorMapper.getResponseTo(creator);
    }

    @Override
    public boolean removeById(Long id) {
        if (jdbcTemplate.query("select * from creator where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Creator.class)).isEmpty()) {
            throw editorNotFoundException(id);
        }
        jdbcTemplate.update("delete from creator where id=?", new Object[]{id});
        return true;
    }

    private static ResourceNotFoundException editorNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find editor with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 23);
    }

    private static ResourceStateException editorStateException() {
        return new ResourceStateException("Failed to create/update editor with specified credentials", HttpStatus.FORBIDDEN.value());
    }
}
