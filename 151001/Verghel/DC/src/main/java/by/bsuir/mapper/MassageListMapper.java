package by.bsuir.mapper;

import by.bsuir.dto.MassageRequestTo;
import by.bsuir.dto.MassageResponseTo;
import by.bsuir.entities.Massage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MassageMapper.class)
public interface MassageListMapper {
    List<Massage> toMassageList(List<MassageRequestTo> Massages);
    List<MassageResponseTo> toMassageResponseList(List<Massage> Massages);
}
