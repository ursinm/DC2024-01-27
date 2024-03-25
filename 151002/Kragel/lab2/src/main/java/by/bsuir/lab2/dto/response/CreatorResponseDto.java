package by.bsuir.lab2.dto.response;

public record CreatorResponseDto(
        Long id,
        String login,
        String password,
        String firstname,
        String lastname
) {
}
