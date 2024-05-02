package by.bsuir.poit.dc.cassandra.services;

/**
 * @author Paval Shlyk
 * @since 08/04/2024
 */
public sealed interface ModerationResult {
    Ok OK = new Ok();

    static Ok ok() {
	return OK;
    }

    static Error withReason(String msg) {
	return new Error(msg);
    }

    record Ok() implements ModerationResult {
    }

    record Error(String reason) implements ModerationResult {
    }

}
