package ru.duckcoder.bankservice.dto.wallet;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransferDTO {
    private Long receiverId;
    @DecimalMin("0.0")
    private Double count;
}
