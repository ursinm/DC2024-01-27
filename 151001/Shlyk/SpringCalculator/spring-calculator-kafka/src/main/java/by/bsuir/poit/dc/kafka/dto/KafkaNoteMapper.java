package by.bsuir.poit.dc.kafka.dto;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
public interface KafkaNoteMapper<IN, OUT> {
    IN unwrapRequest(KafkaUpdateNoteDto dto);

    KafkaNoteDto buildResponse(OUT dto);
}
