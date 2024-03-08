package by.bsuir.poit.dc.cassandra.api.dto.response;

/**
 * @author Paval Shlyk
 * @since 08/03/2024
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

    public PresenceDto orElse(Runnable task) {
	if (!isPresent) {
	    task.run();
	}
	return this;
    }
}
