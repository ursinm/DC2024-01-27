package by.bsuir.springapi.model.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record IssueRequestTo(
        Long id,
        Long creatorId,

        @NotBlank
        @Length(min =  2, max = 64)
        String title,
        
        @Length(min =  4, max = 2048)
        String content
) { }
