package by.harlap.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {

    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
