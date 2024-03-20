package by.bsuir.poit.dc.rest.api.controllers;

import by.bsuir.poit.dc.rest.api.dto.groups.Create;
import by.bsuir.poit.dc.rest.api.dto.groups.Update;
import by.bsuir.poit.dc.rest.api.dto.request.UpdateLabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.services.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/labels")
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    public ResponseEntity<LabelDto> createLabel(
	@RequestBody @Validated(Create.class) UpdateLabelDto dto
    ) {
	var response = labelService.create(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<LabelDto> getLabels() {
	return labelService.getAll();
    }

    @GetMapping("/{labelId}")
    public LabelDto getLabelById(
	@PathVariable long labelId
    ) {
	return labelService.getById(labelId);
    }

    @PutMapping
    public LabelDto updateLabelById(
	@RequestBody @Validated(Update.class) UpdateLabelDto dto
    ) {
	long labelId = dto.id();
	return labelService.update(labelId, dto);
    }

    @DeleteMapping("/{labelId}")
    public Object deleteLabelById(
	@PathVariable long labelId
    ) {
	return labelService.delete(labelId);
    }
}
