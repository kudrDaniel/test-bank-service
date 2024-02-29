package ru.duckcoder.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.duckcoder.bankservice.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
