package ru.duckcoder.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duckcoder.bankservice.model.Phone;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findByPhone(String phone);
    long countByUserId(Long userId);
    boolean existsByPhone(String phone);
}
