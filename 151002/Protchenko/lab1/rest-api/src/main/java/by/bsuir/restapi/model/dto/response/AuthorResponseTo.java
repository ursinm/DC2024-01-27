package by.bsuir.restapi.model.dto.response;

public record AuthorResponseTo(
        Long id,
        String login,
        String password,
        String firstname,
        String lastname
) {
}
