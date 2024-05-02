package by.bsuir.springapi.service;

import by.bsuir.springapi.model.entity.Issue;
import by.bsuir.springapi.model.entity.Post;
import by.bsuir.springapi.model.request.PostRequestTo;
import by.bsuir.springapi.model.response.PostResponseTo;
import by.bsuir.springapi.service.exceptions.ResourceNotFoundException;
import by.bsuir.springapi.service.exceptions.ResourceStateException;
import by.bsuir.springapi.service.mapper.IssueMapper;
import by.bsuir.springapi.service.mapper.PostMapper;
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
public class PostgresPostService implements RestService<PostRequestTo, PostResponseTo>{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PostMapper postMapper;

    @Override
    public List<PostResponseTo> findAll() {
        return jdbcTemplate.query("select * from post", new BeanPropertyRowMapper<>(Post.class)).stream()
                .map(postMapper::getResponseTo).collect(Collectors.toList());
    }

    @Override
    public PostResponseTo findById(Long id) {
        return jdbcTemplate.query("select * from post where id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Post.class)).stream()
                .map(postMapper::getResponseTo)
                .findFirst()
                .orElseThrow(() -> postNotFoundException(id));
    }

    @Override
    public PostResponseTo create(PostRequestTo creatorTo) {
        Post post = postMapper.getComment(creatorTo);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = null;
        try {
            number = insert.withTableName("post").usingGeneratedKeyColumns("id")
                    .executeAndReturnKey(new BeanPropertySqlParameterSource(post));
        } catch (Exception e) {
            throw postStateException();
        }
        post.setId(number.longValue());
        return postMapper.getResponseTo(post);
    }

    @Override
    public PostResponseTo update(PostRequestTo creatorTo) {
        Post post = postMapper.getComment(creatorTo);
        try {
            jdbcTemplate.update("update post set issueid=?, p_content=? where id=?",
                    new Object[]{post.getIssueId(), post.getP_content(), post.getId()});
        } catch (Exception e) {
            throw postStateException();
        }
        return postMapper.getResponseTo(post);
    }

    @Override
    public boolean removeById(Long id) {
        if (jdbcTemplate.query("select * from post where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Post.class)).isEmpty()) {
            throw postNotFoundException(id);
        }
        jdbcTemplate.update("delete from post where id=?", new Object[]{id});
        return true;
    }
    private static ResourceNotFoundException postNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find news with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 53);
    }

    private static ResourceStateException postStateException() {
        return new ResourceStateException("Failed to create/update news with specified credentials", HttpStatus.CONFLICT.value() * 100 + 54);
    }
}
