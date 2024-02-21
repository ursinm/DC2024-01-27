package by.bsuir.services;

import by.bsuir.dao.LabelDao;
import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.entities.Label;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.LabelListMapper;
import by.bsuir.mapper.LabelMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class LabelService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    LabelDao labelDao;
    @Autowired
    LabelListMapper labelListMapper;

    public LabelResponseTo getLabelById(@Min(0) Long id) throws NotFoundException {
        Optional<Label> label = labelDao.findById(id);
        return label.map(value -> labelMapper.labelToLabelResponse(value)).orElseThrow(() -> new NotFoundException("Label not found!", 40004L));
    }

    public List<LabelResponseTo> getLabels() {
        return labelListMapper.toLabelResponseList(labelDao.findAll());
    }

    public LabelResponseTo saveLabel(@Valid LabelRequestTo label) {
        Label labelToSave = labelMapper.labelRequestToLabel(label);
        return labelMapper.labelToLabelResponse(labelDao.save(labelToSave));
    }

    public void deleteLabel(@Min(0) Long id) throws DeleteException {
        labelDao.delete(id);
    }

    public LabelResponseTo updateLabel(@Valid LabelRequestTo label) throws UpdateException {
        Label labelToUpdate = labelMapper.labelRequestToLabel(label);
        return labelMapper.labelToLabelResponse(labelDao.update(labelToUpdate));
    }

    public LabelResponseTo getLabelByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Label> label = labelDao.getLabelByIssueId(issueId);
        return label.map(value -> labelMapper.labelToLabelResponse(value)).orElseThrow(() -> new NotFoundException("Label not found!", 40004L));
    }
}
