package by.haritonenko.jpa.util.dto.author.response;

import by.haritonenko.jpa.dto.response.AuthorResponseDto;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;


@AllArgsConstructor
@NoArgsConstructor(staticName = "author")
@With
public class UpdateAuthorResponseTestBuilder implements TestBuilder<AuthorResponseDto> {

    private Long id = 1L;
    private String login = "author";
    private String password = "fdscvgrfds";
    private String firstname = "first";
    private String lastname = "last";

    @Override
    public AuthorResponseDto build() {
        return new AuthorResponseDto(id, login, password, firstname, lastname);
    }
}