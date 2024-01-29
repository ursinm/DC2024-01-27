package by.bsuir.dao.impl;

import by.bsuir.dao.LabelDao;
import by.bsuir.entities.Issue;
import by.bsuir.entities.Label;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LabelDaoImpl implements LabelDao {
    @Override
    public Label createLabel(String label, Issue issue) {
        return null;
    }

    @Override
    public List<Label> getLabels() {
        return null;
    }

    @Override
    public Label getLabelById(Long Id) {
        return null;
    }

    @Override
    public Label updateLabel(Long id, Label newLabel) {
        return null;
    }

    @Override
    public void deleteLabel(Long id) {

    }

    @Override
    public List<Label> getLabelsByIssueId(Long issueId) {
        return null;
    }
}
