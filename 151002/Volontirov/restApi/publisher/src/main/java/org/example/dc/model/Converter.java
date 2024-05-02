package org.example.dc.model;

import org.springframework.stereotype.Service;

@Service
public class Converter {
    public EditorDto convert(Editor editor) {
        EditorDto editorDto = new EditorDto();
        editorDto.setId(editor.getId());
        editorDto.setLastname(editor.getLastname());
        editorDto.setFirstname(editor.getFirstname());
        editorDto.setLogin(editor.getLogin());
        editorDto.setPassword(editor.getPassword());
        return editorDto;
    }

    public Editor convert(EditorDto editorDto) {
        Editor editor = new Editor();
        editor.setId(editorDto.getId());
        editor.setLastname(editorDto.getLastname());
        editor.setFirstname(editorDto.getFirstname());
        editor.setLogin(editorDto.getLogin());
        editor.setPassword(editorDto.getPassword());
        return editor;
    }

    public Issue convert(IssueDto issueDto) {
        Issue issue = new Issue();
        issue.setTitle(issueDto.getTitle());
        issue.setEditor_id(issueDto.getEditorId());
        issue.setContent(issueDto.getContent());
        issue.setCreated(issueDto.getCreated());
        issue.setModified(issueDto.getModified());
        issue.setId(issueDto.getId());
        return issue;
    }

    public IssueDto convert(Issue issue) {
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle(issue.getTitle());
        issueDto.setEditorId(issue.getEditor_id());
        issueDto.setContent(issue.getContent());
        issueDto.setCreated(issue.getCreated());
        issueDto.setModified(issue.getModified());
        issueDto.setId(issue.getId());
        return issueDto;
    }

    public NoteDto convert(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setContent(note.getContent());
        noteDto.setIssueId(note.getIssue_id());
        return noteDto;
    }

    public Note convert(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setContent(noteDto.getContent());
        note.setIssue_id(noteDto.getIssueId());
        return note;
    }

    public Tag convert(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setId(tagDto.getId());
        return tag;
    }

    public TagDto convert(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());
        tagDto.setId(tag.getId());
        return tagDto;
    }
}