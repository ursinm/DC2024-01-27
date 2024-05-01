package by.bsuir.news.dto.response;

import by.bsuir.news.entity.Note;

public class NoteResponseTo implements IResponseTO<Note, NoteResponseTo> {
    private Long id;
    private String content;
    private Long newsId;
    @Override
    public NoteResponseTo toModel(Note source) {
        NoteResponseTo model = new NoteResponseTo();
        model.id = source.getId();
        model.content = source.getContent();
        model.newsId = source.getNewsId();
        return model;
    }

    public static NoteResponseTo toResponse(Note source) {
        NoteResponseTo model = new NoteResponseTo();
        model.id = source.getId();
        model.content = source.getContent();
        model.newsId = source.getNews().getId();
        return model;
    }

    public NoteResponseTo() {

    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
