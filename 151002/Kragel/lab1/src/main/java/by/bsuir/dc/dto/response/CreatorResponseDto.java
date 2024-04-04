package by.bsuir.dc.dto.response;

public record CreatorResponseDto(
        Long id,
        String login,
        String password,
        String firstname,
        String lastname
) {
}
