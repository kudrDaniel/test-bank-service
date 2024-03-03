package ru.duckcoder.bankservice.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class UserParamsDTO {
    private String fullName;
    @Email
    private String email;
    @Length(min = 10, max = 10)
    private String phone;
    private LocalDate birthDate;
}
