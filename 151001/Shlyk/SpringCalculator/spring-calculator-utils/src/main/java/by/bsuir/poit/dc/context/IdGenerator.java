package by.bsuir.poit.dc.context;

import java.util.UUID;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
public class IdGenerator {
    public long nextLong() {
	return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
