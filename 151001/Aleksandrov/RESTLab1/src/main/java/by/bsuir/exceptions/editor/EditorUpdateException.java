package by.bsuir.exceptions.editor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorUpdateException extends RuntimeException{
    private String message;
    private int status;
}
