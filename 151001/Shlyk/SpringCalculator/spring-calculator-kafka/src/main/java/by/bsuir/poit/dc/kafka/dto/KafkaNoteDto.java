package by.bsuir.poit.dc.kafka.dto;

/**
 * @author Paval Shlyk
 * @since 04/04/2024
 */
public record KafkaNoteDto(
    long id,
    long newsId,
    String content) {
}
