package by.bsuir.dc.rest_basics.entities.dtos.response;

public record AuthorResponseTo(
    Long id,
    String login,
    String firstname,
    String lastname) {
}
