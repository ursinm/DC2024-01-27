package by.bsuir.news.dto.request;

import by.bsuir.news.entity.Editor;

public class EditorRequestTo {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;

    public static Editor fromRequest(EditorRequestTo request) {
        Editor editor = new Editor();
        editor.setId(request.id);
        editor.setLogin(request.login);
        editor.setFirstname(request.firstname);
        editor.setLastname(request.lastname);
        editor.setPassword(request.password);
        return editor;
    }

    public EditorRequestTo() {

    }

    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
