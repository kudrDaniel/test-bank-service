package ru.duckcoder.bankservice.dto.email;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCreateDTO {
    @Email
    private String email;
}
