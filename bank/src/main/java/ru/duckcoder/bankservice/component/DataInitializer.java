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
import ru.duckcoder.bankservice.repository.PhoneRepository;
import ru.duckcoder.bankservice.repository.UserRepository;
import ru.duckcoder.bankservice.repository.WalletRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 5; i++) {
            createUser(
                    "Test User " + i,
                    i + "1234567",
                    LocalDate.of(1980, 1, 1 + i),
                    "testUser" + i + "@example.com",
                    "987654321" + i,
                    (1 + i) * 100.0
            );
        }
    }

    private void createUser(String fullName, String password, LocalDate birthDate, String email, String phone, Double deposit) {
        Email defaultEmail = new Email();
        if (emailRepository.existsByEmail(email)) {
            return;
        }
        defaultEmail.setEmail(email);
        Phone defaultPhone = new Phone();
        if (phoneRepository.existsByPhone(phone)) {
            return;
        }
        defaultPhone.setPhone(phone);
        Wallet defaultWallet = new Wallet();
        defaultWallet.setDeposit(deposit);
        User defaultUser = new User();
        defaultUser.setFullName(fullName);
        defaultUser.setEmails(List.of(defaultEmail));
        defaultUser.setPhones(List.of(defaultPhone));
        defaultUser.setWallet(defaultWallet);
        defaultUser.setBirthDate(birthDate);
        defaultUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(defaultUser);
        defaultEmail.setUser(defaultUser);
        emailRepository.save(defaultEmail);
        defaultPhone.setUser(defaultUser);
        phoneRepository.save(defaultPhone);
        defaultWallet.setUser(defaultUser);
        walletRepository.save(defaultWallet);
    }
}
