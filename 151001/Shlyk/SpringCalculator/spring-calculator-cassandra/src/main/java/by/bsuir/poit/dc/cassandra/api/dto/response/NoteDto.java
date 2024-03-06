package by.bsuir.poit.dc.cassandra.api.dto.response;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
public record NoteDto(
    long id,
    String country,
    long newsId,
    String content) {
}
