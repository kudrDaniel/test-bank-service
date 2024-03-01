package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.duckcoder.bankservice.dto.wallet.WalletDTO;
import ru.duckcoder.bankservice.dto.wallet.WalletTransferDTO;
import ru.duckcoder.bankservice.exception.ResourceNotFoundException;
import ru.duckcoder.bankservice.mapper.WalletMapper;
import ru.duckcoder.bankservice.model.Wallet;
import ru.duckcoder.bankservice.repository.WalletRepository;
import ru.duckcoder.bankservice.util.UserUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserUtils userUtils;

    @Transactional
    public WalletDTO transfer(Long senderId, WalletTransferDTO dto) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), senderId))
            throw new AccessDeniedException("Access denied for transferring from another user");
        Wallet senderWallet = walletRepository.findByUserId(senderId)
                .orElseThrow(() -> new ResourceNotFoundException(Wallet.class, "userId", senderId));
        Wallet receiverWallet = walletRepository.findByUserId(dto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException(Wallet.class, "userId", dto.getReceiverId()));

        if (senderWallet.removeFromDeposit(dto.getCount())) {
            receiverWallet.addToDeposit(dto.getCount());
        } else
            throw new NoSuchElementException("Deposit cannot be below zero");

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        return walletMapper.map(senderWallet);
    }

    @Scheduled(fixedDelay = 60000L)
    @Transactional
    public void updateAccrual() {
        List<Wallet> walletModels = walletRepository.findAll();
        for (Wallet walletModel : walletModels) {
            walletModel.changeAccrual();
        }
    }
}
