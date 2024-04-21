package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.CreateEditorDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class CreateEditorDtoTestBuilder implements TestBuilder<CreateEditorDto> {

    private String login = "editor2456";
    private String password = "asdfghj241";
    private String firstname = "firstname1294";
    private String lastname = "lastname6724";

    @Override
    public CreateEditorDto build() {
        return new CreateEditorDto(login, password, firstname, lastname);
    }
}
