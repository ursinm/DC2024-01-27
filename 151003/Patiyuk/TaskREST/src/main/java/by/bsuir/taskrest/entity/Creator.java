package by.bsuir.taskrest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creator implements BaseEntity<Long> {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}
