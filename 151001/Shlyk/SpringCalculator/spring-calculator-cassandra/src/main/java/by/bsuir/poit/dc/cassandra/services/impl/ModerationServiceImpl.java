package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.exceptions.ContentNotValidException;
import by.bsuir.poit.dc.cassandra.services.ModerationResult;
import by.bsuir.poit.dc.cassandra.services.ModerationService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 08/04/2024
 */
@Service
public class ModerationServiceImpl implements ModerationService {
    private final List<String> bookieWords = List.of("fuck", "bitch");

    @Override
    public ModerationResult verify(@NonNull String content) {
	boolean hasBookies = bookieWords.parallelStream()
				 .anyMatch(content::contains);
	if (hasBookies) {
	    return ModerationResult.withReason("The provided content holds bookies");
	}
	return ModerationResult.ok();
    }
}
