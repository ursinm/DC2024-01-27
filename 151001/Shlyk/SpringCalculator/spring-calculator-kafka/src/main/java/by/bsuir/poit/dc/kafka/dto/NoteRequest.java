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
    public static NoteRequest withId(@NonNull RequestEvent event, long id) {
	return new NoteRequest(event, id, null);
    }

    public static NoteRequest empty(@NonNull RequestEvent event) {
	return new NoteRequest(event, null, null);
    }
}
