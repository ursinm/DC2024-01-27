package by.bsuir.poit.dc.kafka.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
@Builder
public record NoteRequest(
    @NonNull RequestEvent event,
    Long id,
    @Nullable KafkaUpdateNoteDto dto
) {

}
