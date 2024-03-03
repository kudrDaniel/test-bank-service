package ru.duckcoder.bankservice.dto.phone;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PhoneUpdateDTO {
    @NotBlank
    @Length(min = 10, max = 10)
    private String phone;
}
