package ru.duckcoder.bankservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateDTO {
    @Length(min = 5, max = 128)
    private String fullName;
    @NotNull
    private LocalDate birthDate;
    @Email
    private String email;
    @Range(min = 8, max = 64)
    private String password;
    @NotBlank
    private String phone;
    @NotNull
    private Double deposit;
}
