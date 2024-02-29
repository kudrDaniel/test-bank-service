package ru.duckcoder.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.User;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<User> findUserByEmail(String email);

    Boolean existsByEmail(String email);
}
