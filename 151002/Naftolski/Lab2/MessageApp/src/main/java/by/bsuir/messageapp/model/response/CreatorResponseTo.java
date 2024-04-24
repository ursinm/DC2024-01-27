package by.bsuir.messageapp.model.response;

public record CreatorResponseTo(
        Long id,
        String login,
        String password,
        String firstname,
        String lastname
) {
}
