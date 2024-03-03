package ru.duckcoder.bankservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            return null;
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }
}
