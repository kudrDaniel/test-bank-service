package ru.duckcoder.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.User;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, long id);
}
