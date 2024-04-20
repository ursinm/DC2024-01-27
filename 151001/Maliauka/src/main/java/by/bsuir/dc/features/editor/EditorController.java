package by.bsuir.dc.features.editor;

import by.bsuir.dc.features.editor.dto.CreateEditorDto;
import by.bsuir.dc.features.editor.dto.EditorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/editors")
@RequiredArgsConstructor
public class EditorController {
    private final EditorService editorService;

    @PostMapping
    public ResponseEntity<EditorResponseDto> createEditor(@RequestBody CreateEditorDto createEditorDto) {
        var editor = editorService.addEditor(createEditorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(editor);
    }

    @GetMapping
    public ResponseEntity<List<EditorResponseDto>> getAllEditors() {
        var editors = editorService.getAllEditors();
        return ResponseEntity.ok(editors);
    }

    @GetMapping("/{editorId}")
    public ResponseEntity<EditorResponseDto> getById(@PathVariable Long editorId) {
        var editor = editorService.getById(editorId);
        return ResponseEntity.ok(editor);
    }

    @PutMapping("/{editorId}")
    public ResponseEntity<EditorResponseDto> updateEditorById(@PathVariable Long editorId,
                                                              @RequestBody CreateEditorDto createEditorDto){
        var editor = editorService.updateById(editorId, createEditorDto);
        return ResponseEntity.ok(editor);
    }

    @DeleteMapping("/{editorId}")
    public ResponseEntity<EditorResponseDto> deleteById(@PathVariable Long editorId) {
        var editor = editorService.deleteById(editorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(editor);
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<EditorResponseDto> getEditorByNewsId(@PathVariable Long newsId) {
        var editor = editorService.getByNewsId(newsId);
        return ResponseEntity.ok(editor);
    }
}
