package by.bsuir.rv.util.converter.editor;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import by.bsuir.rv.util.converter.IConverter;
import org.springframework.stereotype.Component;

@Component
public class EditorConverter implements IConverter<Editor, EditorResponseTo, EditorRequestTo> {
    public EditorResponseTo convertToResponse(Editor editor) {
        return new EditorResponseTo(editor.getId(), editor.getLogin(), editor.getPassword(), editor.getFirstname(), editor.getLastname());
    }

    @Override
    public Editor convertToEntity(EditorRequestTo requestTo) {
        return new Editor(requestTo.getId(), requestTo.getLogin(), requestTo.getPassword(), requestTo.getFirstname(), requestTo.getLastname());
    }
}
