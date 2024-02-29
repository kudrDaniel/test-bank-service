package ru.duckcoder.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duckcoder.bankservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
