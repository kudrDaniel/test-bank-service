package ru.duckcoder.bankservice.dto.email;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailUpdateDTO {
    @Email
    private String email;
}
