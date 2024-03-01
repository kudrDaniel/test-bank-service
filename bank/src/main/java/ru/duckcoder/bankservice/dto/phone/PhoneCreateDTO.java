package ru.duckcoder.bankservice.dto.phone;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PhoneCreateDTO {
    @Length(min = 10, max = 10)
    private String phone;
}
