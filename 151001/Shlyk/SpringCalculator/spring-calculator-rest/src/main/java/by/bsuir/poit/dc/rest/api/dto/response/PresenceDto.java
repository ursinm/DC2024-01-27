package by.bsuir.poit.dc.rest.api.dto.response;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 22/02/2024
 */
public record PresenceDto(boolean isPresent) {
    public static PresenceDto wrap(boolean isDeleted) {
	return new PresenceDto(isDeleted);
    }

    public PresenceDto ifPresent(Runnable task) {
	if (isPresent) {
	    task.run();
	}
	return this;
    }

    public boolean unwrap() {
	return isPresent;
    }

    public PresenceDto orElse(Runnable task) {
	if (!isPresent) {
	    task.run();
	}
	return this;
    }
}
