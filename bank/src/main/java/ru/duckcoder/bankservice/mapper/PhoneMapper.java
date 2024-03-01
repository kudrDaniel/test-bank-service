package ru.duckcoder.bankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.duckcoder.bankservice.dto.phone.PhoneCreateDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneUpdateDTO;
import ru.duckcoder.bankservice.model.Phone;

@Mapper(uses = ReferenceMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PhoneMapper {
    public abstract Phone map(PhoneCreateDTO dto);

    public abstract PhoneDTO map(Phone model);

    public abstract void update(PhoneUpdateDTO dto, @MappingTarget Phone model);
}
