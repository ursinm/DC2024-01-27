package by.bsuir.poit.dc.kafka.dto;

import lombok.Builder;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
@Builder
public record NoteResponse(
    ResponseEvent status,
    List<KafkaNoteDto> list
) implements StatusResponse {

    public static NoteResponse withStatus(ResponseEvent event) {
	return new NoteResponse(event, null);
    }
}
