package ru.duckcoder.bankservice.dto.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank
    @Length(min = 5, max = 128)
    private String fullName;
    @NotBlank
    @Length(min = 3, max = 64)
    private String username;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 64)
    private String password;
    @NotBlank
    private String phone;
    @DecimalMin("0.0")
    private Double deposit;
}
