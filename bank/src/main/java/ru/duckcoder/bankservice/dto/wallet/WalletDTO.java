package ru.duckcoder.bankservice.dto.wallet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDTO {
    private Long deposit;
    private Long accrual;
}
