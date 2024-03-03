package ru.duckcoder.bankservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.duckcoder.bankservice.dto.wallet.WalletDTO;
import ru.duckcoder.bankservice.dto.wallet.WalletTransferDTO;
import ru.duckcoder.bankservice.service.WalletService;

@Tag(name = "Wallets", description = "The Wallet API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @Operation(summary = "Returns wallet", description = "Get wallet by user id")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Wallet founded",
            content = @Content(mediaType = "application/json",
            schema = @Schema(name = "Wallet", implementation = WalletDTO.class)))
    })
    @GetMapping("/{id}/wallet")
    @ResponseStatus(HttpStatus.OK)
    public WalletDTO show(@PathVariable Long id) {
        return walletService.findByUserId(id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{senderId}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public WalletDTO transfer(@PathVariable Long senderId, @Valid @RequestBody WalletTransferDTO dto) {
        return walletService.transfer(senderId, dto);
    }
}
