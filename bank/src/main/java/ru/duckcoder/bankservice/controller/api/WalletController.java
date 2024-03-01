package ru.duckcoder.bankservice.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.duckcoder.bankservice.dto.wallet.WalletDTO;
import ru.duckcoder.bankservice.dto.wallet.WalletTransferDTO;
import ru.duckcoder.bankservice.service.WalletService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PutMapping("/{senderId}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public WalletDTO transfer(@PathVariable Long senderId, @Valid @RequestBody WalletTransferDTO dto) {
        return walletService.transfer(senderId, dto);
    }
}
