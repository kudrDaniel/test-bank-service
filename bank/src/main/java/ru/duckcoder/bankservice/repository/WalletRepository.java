package ru.duckcoder.bankservice.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.duckcoder.bankservice.model.Wallet;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Wallet> findAll();

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Wallet> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Wallet> findByUserId(Long userId);
}
