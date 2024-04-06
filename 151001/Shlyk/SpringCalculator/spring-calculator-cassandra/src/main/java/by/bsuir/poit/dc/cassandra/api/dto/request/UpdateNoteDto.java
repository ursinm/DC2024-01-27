package by.bsuir.poit.dc.cassandra.api.dto.request;

import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Builder(toBuilder = true)
public record UpdateNoteDto(
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    Long id,
    Long newsId,
    String content
) {

}
