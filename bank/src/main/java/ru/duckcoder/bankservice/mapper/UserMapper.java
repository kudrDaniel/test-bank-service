package ru.duckcoder.bankservice.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.duckcoder.bankservice.dto.user.UserCreateDTO;
import ru.duckcoder.bankservice.dto.user.UserDTO;
import ru.duckcoder.bankservice.model.User;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract User map(UserCreateDTO dto);

    public abstract UserDTO map(User model);

    @BeforeMapping
    public void encryptPassword(UserCreateDTO dto) {
        String password = dto.getPassword();
        dto.setPassword(passwordEncoder.encode(password));
    }
}
