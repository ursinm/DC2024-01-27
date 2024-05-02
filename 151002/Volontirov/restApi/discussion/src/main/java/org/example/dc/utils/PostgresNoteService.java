package org.example.dc.utils;

import org.example.dc.model.Converter;
import org.example.dc.model.Note;
import org.example.dc.model.NoteDto;
import org.example.dc.services.NoteService;
import org.example.dc.services.impl.CassandraNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresNoteService implements NoteService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Converter converter;

    CassandraNoteService noteService = new CassandraNoteService();
    @Override
    public List<NoteDto> getNotes() {
        return jdbcTemplate.query("select * from tbl_note", new BeanPropertyRowMapper<>(Note.class)).stream()
                .map(note -> converter.convert(note)).collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(int id) {
        return converter.convert(jdbcTemplate.query("select * from tbl_note where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Note.class)).get(0));
    }

    @Override
    public boolean delete(int id) throws Exception {
        if(jdbcTemplate.query("select * from tbl_note", new BeanPropertyRowMapper<>(Note.class)).isEmpty()) {
            throw new Exception();
        }
        jdbcTemplate.update("delete from tbl_note where id=?", new Object[]{id});
        return true;
    }

    @Override
    public NoteDto create(NoteDto noteDto) {
        Note note = converter.convert(noteDto);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getDataSource());
        Number number = insert.withTableName("tbl_note").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new BeanPropertySqlParameterSource(note));
        noteDto.setId(number.intValue());
        return noteDto;
    }

    @Override
    public NoteDto update(NoteDto noteDto) {
        Note note = converter.convert(noteDto);
        jdbcTemplate.update("update tbl_note set issueId=?, content=? where id=?",
                new Object[]{note.getIssueId(), note.getContent(), note.getId()});
        return noteDto;
    }
}