package ru.duckcoder.bankservice.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTransferDTO {
    private Long receiverId;
    @DecimalMin("0.0")
    private Double count;
}
