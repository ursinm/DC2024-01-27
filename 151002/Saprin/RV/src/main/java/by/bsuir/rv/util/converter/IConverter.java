package by.bsuir.rv.util.converter;

import by.bsuir.rv.bean.IdentifiedBean;

public interface IConverter<Entity extends IdentifiedBean, ResponseTo extends IdentifiedBean, RequestTo extends IdentifiedBean > {
    Entity convertToEntity(RequestTo requestTo);
}
