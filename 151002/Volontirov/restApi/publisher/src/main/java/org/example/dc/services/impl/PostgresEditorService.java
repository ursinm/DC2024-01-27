package org.example.dc.services.impl;

import org.example.dc.model.Converter;
import org.example.dc.model.Editor;
import org.example.dc.model.EditorDto;
import org.example.dc.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresEditorService implements EditorService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    @Override
    public List<EditorDto> getEditors() {
        return jdbcTemplate.query("select * from tbl_editor", new BeanPropertyRowMapper<>(Editor.class)).stream()
                .map(editor -> converter.convert(editor)).collect(Collectors.toList());
    }

    @Override
    public EditorDto getEditorById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_editor where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Editor.class)).get(0));
    }

    @Override
    public EditorDto createEditor(EditorDto editorDto) {
        Editor editor = converter.convert(editorDto);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_editor").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(editor));
        editorDto.setId(number.intValue());
        return editorDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        if (jdbcTemplate.query("select * from tbl_editor where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Editor.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_editor where id=?", new Object[]{id});
        return true;
    }

    @Override
    public EditorDto updateEditor(EditorDto editorDto) {
        Editor editor = converter.convert(editorDto);
        jdbcTemplate.update("update tbl_editor set login=?, password=?, firstname=?, lastname=? where id=?",
                new Object[]{editor.getLogin(), editor.getPassword(),
                        editorDto.getFirstname(), editorDto.getLastname(), editorDto.getId()});
        return editorDto;
    }
}