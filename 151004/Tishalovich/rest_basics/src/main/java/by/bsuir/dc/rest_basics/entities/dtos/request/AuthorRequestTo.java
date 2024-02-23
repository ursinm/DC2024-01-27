package by.bsuir.dc.rest_basics.entities.dtos.request;

public record AuthorRequestTo (
        Long id,
        String login,
        String password,
        String firstname,
        String lastname) {
}
