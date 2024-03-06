package by.bsuir.poit.dc.cassandra.api.mappers;

import by.bsuir.poit.dc.cassandra.api.mappers.config.CentralMapperConfig;
import org.mapstruct.*;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    config = CentralMapperConfig.class
)
public abstract class NoteMapper {

}