package ru.duckcoder.bankservice.controller.util;

import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;

public class DTOUtils {
    public static Email buildEmail(String email) {
        Email model = new Email();
        model.setEmail(email);
        return model;
    }

    public static Phone buildPhone(String phone) {
        Phone model = new Phone();
        model.setPhone(phone);
        return model;
    }
}
