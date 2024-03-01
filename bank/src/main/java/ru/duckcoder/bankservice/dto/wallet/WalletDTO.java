package ru.duckcoder.bankservice.dto.wallet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDTO {
    private Double deposit;
    private Double accrual;
}
