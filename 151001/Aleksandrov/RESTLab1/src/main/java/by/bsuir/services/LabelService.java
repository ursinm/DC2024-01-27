package by.bsuir.services;

import by.bsuir.dao.LabelDao;
import by.bsuir.dto.LabelRequestTo;
import by.bsuir.dto.LabelResponseTo;
import by.bsuir.entities.Label;
import by.bsuir.mapper.LabelListMapper;
import by.bsuir.mapper.LabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    LabelDao labelDao;
    @Autowired
    LabelListMapper labelListMapper;

    public LabelResponseTo getLabelById(Long id) {
        Optional<Label> label = labelDao.findById(id);
        return label.map(value -> labelMapper.labelToLabelResponse(value)).orElse(null);
    }

    public List<LabelResponseTo> getLabels() {
        return labelListMapper.toLabelResponseList(labelDao.findAll());
    }

    public LabelResponseTo saveLabel(LabelRequestTo label){
        Label labelToSave = labelMapper.labelRequestToLabel(label);
        return labelMapper.labelToLabelResponse(labelDao.save(labelToSave));
    }

    public void deleteLabel(Long id){
        labelDao.delete(id);
    }

    public LabelResponseTo updateLabel(LabelRequestTo label){
        Label labelToUpdate = labelMapper.labelRequestToLabel(label);
        return labelMapper.labelToLabelResponse(labelDao.update(labelToUpdate));
    }
}
