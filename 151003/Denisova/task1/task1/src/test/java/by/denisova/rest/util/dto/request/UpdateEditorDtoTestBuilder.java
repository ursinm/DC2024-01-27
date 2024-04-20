package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.UpdateEditorDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "editor")
@With
public class UpdateEditorDtoTestBuilder implements TestBuilder<UpdateEditorDto> {

    private Long id = 1L;
    private String login = "editor";
    private String password = "fdscvgrfds";
    private String firstname = "first";
    private String lastname = "last";

    @Override
    public UpdateEditorDto build() {
        return new UpdateEditorDto(id, login, password, firstname, lastname);
    }
}