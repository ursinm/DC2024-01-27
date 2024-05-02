package by.bsuir.publisher.model.response;

public record CreatorResponseTo(
        Long id,
        String login,
        String password,
        String firstname,
        String lastname
) {
}
