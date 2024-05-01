package by.bsuir.news.dto.request;

import by.bsuir.news.entity.News;

public class NewsRequestTo {
    private Long id;
    private String title;
    private String content;
    private Long editorId;

    public NewsRequestTo() {

    }

    public static News fromRequest(NewsRequestTo request) {
        News news = new News();
        news.setId(request.id);
        news.setTitle(request.title);
        news.setContent(request.content);
        return news;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
