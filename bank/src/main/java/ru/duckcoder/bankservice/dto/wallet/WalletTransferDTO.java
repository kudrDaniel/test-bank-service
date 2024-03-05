package ru.duckcoder.bankservice.dto.wallet;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransferDTO {
    private Long receiverId;
    @Min(0)
    private Long count;
}
