package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.rest.api.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import by.bsuir.poit.dc.rest.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 01/02/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public List<NoteDto> getAllNotes() {
	return noteService.getAll();
    }

    @GetMapping("/{noteId}")
    public NoteDto getNoteById(
	@PathVariable long noteId
    ) {
	return noteService.getById(noteId);
    }

    @PatchMapping("/{noteId}")
    public NoteDto updateNoteById(
	@PathVariable long noteId,
	@RequestBody @Validated(Update.class) UpdateNoteDto dto
    ) {
	return noteService.update(noteId, dto);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteNoteById(
	@PathVariable long noteId
    ) {
	boolean isDeleted = noteService.delete(noteId);
	HttpStatus status = isDeleted ?
				HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }

}
