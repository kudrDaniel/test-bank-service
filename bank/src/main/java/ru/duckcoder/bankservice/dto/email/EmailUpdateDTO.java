package ru.duckcoder.bankservice.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailUpdateDTO {
    @NotBlank
    @Email
    private String email;
}
