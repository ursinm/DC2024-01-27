package by.bsuir.dc.rest_basics.entities.dtos.request;

public record AuthorRequestTo (
        Long id,
        String login,
        String password,
        String firstName,
        String lastName) {
}
