package ru.duckcoder.bankservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.Wallet;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String fullName;
    private List<Email> emails;
    private List<Phone> phones;
    private Wallet wallet;
}
