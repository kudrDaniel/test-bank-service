package ru.duckcoder.bankservice.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private LocalDate birthDate;
    private List<Email> emails;
    private List<Phone> phones;
}
