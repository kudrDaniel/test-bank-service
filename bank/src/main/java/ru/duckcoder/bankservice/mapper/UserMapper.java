package ru.duckcoder.bankservice.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.duckcoder.bankservice.dto.UserCreateDTO;
import ru.duckcoder.bankservice.dto.UserDTO;
import ru.duckcoder.bankservice.dto.UserUpdateDTO;
import ru.duckcoder.bankservice.model.User;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "emails.email", source = "email")
    @Mapping(target = "phones.phone", source = "phone")
    @Mapping(target = "wallet.deposit", source = "deposit")
    public abstract User map(UserCreateDTO dto);

    public abstract UserDTO map(User model);

    public abstract void update(UserUpdateDTO dto, @MappingTarget User model);

    @BeforeMapping
    public void encryptPassword(UserCreateDTO dto) {
        String password = dto.getPassword();
        dto.setPassword(passwordEncoder.encode(password));
    }
}
