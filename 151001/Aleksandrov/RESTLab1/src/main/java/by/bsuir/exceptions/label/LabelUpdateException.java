package by.bsuir.exceptions.label;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelUpdateException extends RuntimeException{
    private String message;
    private int status;
}

