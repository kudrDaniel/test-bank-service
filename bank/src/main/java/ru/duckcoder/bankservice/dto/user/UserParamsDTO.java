package ru.duckcoder.bankservice.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserParamsDTO {
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
