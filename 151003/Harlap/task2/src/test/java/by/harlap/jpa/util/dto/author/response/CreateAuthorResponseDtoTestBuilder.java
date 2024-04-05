package by.harlap.jpa.util.dto.author.response;

import by.harlap.jpa.dto.response.AuthorResponseDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "author")
@With
public class CreateAuthorResponseDtoTestBuilder implements TestBuilder<AuthorResponseDto> {

    private Long id = 2L;
    private String login = "login";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public AuthorResponseDto build() {
        return new AuthorResponseDto(id, login, password, firstname, lastname);
    }
}
