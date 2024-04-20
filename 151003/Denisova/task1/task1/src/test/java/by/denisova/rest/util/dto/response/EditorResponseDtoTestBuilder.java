package by.denisova.rest.util.dto.response;

import by.denisova.rest.dto.response.EditorResponseDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class EditorResponseDtoTestBuilder implements TestBuilder<EditorResponseDto> {

    private Long id = 1L;
    private String login = "editor2456";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public EditorResponseDto build() {
        return new EditorResponseDto(id, login, password, firstname, lastname);
    }
}
