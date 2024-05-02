package by.bsuir.springapi.model.request;

import org.hibernate.validator.constraints.Length;

public record PostRequestTo(
        Long id,
        Long issueId,
        @Length(min = 2, max = 2048)
        String content) 
{}
