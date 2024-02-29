package ru.duckcoder.bankservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;

import java.util.List;

@Getter
@Setter
public class UserUpdateDTO {
    private JsonNullable<List<Email>> emails;
    private JsonNullable<List<Phone>> phones;
}
