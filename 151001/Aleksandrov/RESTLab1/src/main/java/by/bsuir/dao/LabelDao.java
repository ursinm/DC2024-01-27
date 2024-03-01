package by.bsuir.dao;

import by.bsuir.entities.Label;

import java.util.Optional;

public interface LabelDao extends CrudDao<Label> {
    Optional<Label> getLabelByIssueId (long issueId);
}
