package ru.duckcoder.bankservice.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import ru.duckcoder.bankservice.model.Mappable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    public <T extends Mappable> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id == null ? null : entityManager.find(entityClass, id);
    }
}
