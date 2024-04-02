package by.bsuir.services;

import by.bsuir.dao.MassageDao;
import by.bsuir.dto.MassageRequestTo;
import by.bsuir.dto.MassageResponseTo;
import by.bsuir.entities.Massage;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.MassageListMapper;
import by.bsuir.mapper.MassageMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MassageService {
    @Autowired
    MassageMapper MassageMapper;
    @Autowired
    MassageDao MassageDao;
    @Autowired
    MassageListMapper MassageListMapper;

    public MassageResponseTo getMassageById(@Min(0) Long id) throws NotFoundException {
        Optional<Massage> Massage = MassageDao.findById(id);
        return Massage.map(value -> MassageMapper.MassageToMassageResponse(value)).orElseThrow(() -> new NotFoundException("Massage not found!", 40004L));
    }

    public List<MassageResponseTo> getMassages() {
        return MassageListMapper.toMassageResponseList(MassageDao.findAll());
    }

    public MassageResponseTo saveMassage(@Valid MassageRequestTo Massage) {
        Massage MassageToSave = MassageMapper.MassageRequestToMassage(Massage);
        return MassageMapper.MassageToMassageResponse(MassageDao.save(MassageToSave));
    }

    public void deleteMassage(@Min(0) Long id) throws DeleteException {
        MassageDao.delete(id);
    }

    public MassageResponseTo updateMassage(@Valid MassageRequestTo Massage) throws UpdateException {
        Massage MassageToUpdate = MassageMapper.MassageRequestToMassage(Massage);
        return MassageMapper.MassageToMassageResponse(MassageDao.update(MassageToUpdate));
    }
}
