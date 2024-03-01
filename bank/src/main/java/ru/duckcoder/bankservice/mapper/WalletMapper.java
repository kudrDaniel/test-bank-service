package ru.duckcoder.bankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.duckcoder.bankservice.dto.wallet.WalletDTO;
import ru.duckcoder.bankservice.model.Wallet;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class WalletMapper {
    public abstract WalletDTO map(Wallet model);
}
