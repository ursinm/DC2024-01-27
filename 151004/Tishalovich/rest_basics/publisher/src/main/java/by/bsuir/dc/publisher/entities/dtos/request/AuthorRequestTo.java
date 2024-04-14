package by.bsuir.dc.publisher.entities.dtos.request;

public record AuthorRequestTo (
        Long id,
        String login,
        String password,
        String firstname,
        String lastname) {
}
