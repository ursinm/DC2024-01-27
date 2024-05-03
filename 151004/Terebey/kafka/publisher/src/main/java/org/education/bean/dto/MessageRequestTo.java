package org.education.bean.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MessageRequestTo {
    int id;

    @Min(value = 0)
    @Max(value = 20000)
    int issueId;

    @Size(min = 2, max = 2048, message = "Incorrect content size")
    String content;
}