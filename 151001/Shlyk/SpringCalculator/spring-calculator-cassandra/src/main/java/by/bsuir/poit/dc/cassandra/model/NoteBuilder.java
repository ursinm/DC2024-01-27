package by.bsuir.poit.dc.cassandra.model;

import lombok.RequiredArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 08/04/2024
 */
public class NoteBuilder {
    @RequiredArgsConstructor
    public enum Status {
	PENDING(1), DECLINED(2), APPROVED(3);
	private final int id;

	public short id() {
	    return (short) id;
	}
    }
}
