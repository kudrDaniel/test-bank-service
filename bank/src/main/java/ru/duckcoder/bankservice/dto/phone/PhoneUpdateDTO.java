package ru.duckcoder.bankservice.dto.phone;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PhoneUpdateDTO {
    @Length(min = 10, max = 10)
    private String phone;
}
