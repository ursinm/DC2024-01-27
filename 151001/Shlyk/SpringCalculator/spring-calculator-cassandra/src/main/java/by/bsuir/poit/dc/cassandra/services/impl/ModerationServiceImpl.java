package by.bsuir.poit.dc.cassandra.services.impl;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.model.NoteBuilder;
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

    @Override
    public UpdateNoteDto prepareUpdate(@NonNull UpdateNoteDto dto) {
	final UpdateNoteDto output;
	if (dto.content() != null) {
	    var status = switch (verify(dto.content())) {
		case ModerationResult.Error(String _) -> NoteBuilder.Status.DECLINED;
		case ModerationResult.Ok _ -> NoteBuilder.Status.APPROVED;
	    };
	    output = dto.toBuilder()
			 .status(status.id())
			 .build();
	} else {
	    output = dto;
	}
	return output;
    }

    @Override
    public UpdateNoteDto prepareSave(@NonNull UpdateNoteDto dto) {
	assert dto.content() != null : "The initial content should be non null";
	var status = switch (verify(dto.content())) {
	    case ModerationResult.Error(String _) -> NoteBuilder.Status.DECLINED;
	    case ModerationResult.Ok _ -> NoteBuilder.Status.APPROVED;
	};
	return dto.toBuilder()
		   .status(status.id())
		   .build();
    }
}
