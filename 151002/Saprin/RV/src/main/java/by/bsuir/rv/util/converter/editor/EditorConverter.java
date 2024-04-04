package by.bsuir.rv.util.converter.editor;

import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.dto.EditorRequestTo;
import by.bsuir.rv.dto.EditorResponseTo;
import org.springframework.stereotype.Component;

@Component
public class EditorConverter {
    public EditorResponseTo convertToResponse(Editor editor) {
        return new EditorResponseTo(editor.getEd_id(), editor.getEd_login(), editor.getEd_password(), editor.getEd_firstname(), editor.getEd_lastname());
    }

    public Editor convertToEntity(EditorRequestTo requestTo) {
        return new Editor(requestTo.getId(), requestTo.getLogin(), requestTo.getPassword(), requestTo.getFirstname(), requestTo.getLastname());
    }
}
