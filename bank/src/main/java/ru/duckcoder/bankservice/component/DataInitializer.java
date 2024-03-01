package ru.duckcoder.bankservice.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.model.Wallet;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User defaultUser = new User();
        Email defaultEmail = new Email();
        defaultEmail.setEmail("johnSmith@example.com");
        if (emailRepository.existsByEmail(defaultEmail.getEmail())) {
            return;
        }
        Phone defaultPhone = new Phone();
        defaultPhone.setPhone("9876543210");
        Wallet defaultWallet = new Wallet();
        defaultWallet.setDeposit(100.0);
        defaultUser.setFullName("Smith John Johnson");
        defaultUser.setEmails(List.of(defaultEmail));
        defaultUser.setPhones(List.of(defaultPhone));
        defaultUser.setWallet(defaultWallet);
        defaultUser.setBirthDate(LocalDate.of(1990, 10, 20));
        defaultUser.setPassword(passwordEncoder.encode("12345678"));
        userRepository.save(defaultUser);
    }
}
