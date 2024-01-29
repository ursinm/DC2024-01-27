package by.bsuir.dao;

import by.bsuir.entities.Label;
import by.bsuir.entities.Issue;

import java.util.List;

public interface LabelDao {
    Label createLabel(String label, Issue issue);

    List<Label> getLabels();

    Label getLabelById(Long Id);

    Label updateLabel(Long id, Label newLabel);

    void deleteLabel(Long id);

    List<Label> getLabelsByIssueId(Long issueId);
}
