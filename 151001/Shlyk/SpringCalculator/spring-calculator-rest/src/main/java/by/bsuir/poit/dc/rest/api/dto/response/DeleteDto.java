package by.bsuir.poit.dc.rest.api.dto.response;

/**
 * @author Paval Shlyk
 * @since 22/02/2024
 */
public record DeleteDto(boolean isDeleted) {
    public static DeleteDto wrap(boolean isDeleted) {
	return new DeleteDto(isDeleted);
    }
}
