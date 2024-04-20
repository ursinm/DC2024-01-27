package by.harlap.rest.util.dto.request;

import by.harlap.rest.dto.request.UpdateAuthorDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "author")
@With
public class UpdateAuthorDtoTestBuilder implements TestBuilder<UpdateAuthorDto> {

    private Long id = 1L;
    private String login = "author";
    private String password = "fdscvgrfds";
    private String firstname = "first";
    private String lastname = "last";

    @Override
    public UpdateAuthorDto build() {
        return new UpdateAuthorDto(id, login, password, firstname, lastname);
    }
}