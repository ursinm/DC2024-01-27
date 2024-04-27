package by.bsuir.news.dto.response;

import by.bsuir.news.entity.Editor;
import java.util.List;
import java.util.stream.Collectors;


public class EditorResponseTo implements IResponseTO<Editor, EditorResponseTo> {
    private String login;
    private String firstname;
    private String lastname;
    private Long id;
    private List<NewsResponseTo> news;

    public EditorResponseTo toModel(Editor editor) {
        EditorResponseTo model = new EditorResponseTo();
        model.id = editor.getId();
        model.login = editor.getLogin();
        model.firstname = editor.getFirstname();
        model.lastname = editor.getLastname();
        return model;
    }

    public static EditorResponseTo toResponse(Editor editor) {
        EditorResponseTo model = new EditorResponseTo();
        model.id = editor.getId();
        model.login = editor.getLogin();
        model.firstname = editor.getFirstname();
        model.lastname = editor.getLastname();
        model.news = editor.getNews() != null ? editor.getNews().stream().map(NewsResponseTo::toResponse).collect(Collectors.toList()) : null;
        return model;
    }

    public EditorResponseTo() {

    }

    public void setNews(List<NewsResponseTo> news) {
        this.news = news;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Long getId() {
        return id;
    }
}
