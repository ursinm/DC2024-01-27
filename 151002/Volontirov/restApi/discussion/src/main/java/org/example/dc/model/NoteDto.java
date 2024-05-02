package org.example.dc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private int id;
    private int issueId;
    @Length(min = 2, max = 2048)
    private String content;
}
