package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsManager {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails details) {
        Email email = new Email();
        email.setEmail(details.getUsername());
        User user = new User();
        user.setEmails(List.of(email));
        String encodedPassword = passwordEncoder.encode(details.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        email.setUser(user);
        emailRepository.save(email);
    }

    @Override
    public void updateUser(UserDetails details) {

    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String email) {
        return emailRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return emailRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")).getUser();
    }
}
