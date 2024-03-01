package ru.duckcoder.bankservice.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.duckcoder.bankservice.dto.email.EmailCreateDTO;
import ru.duckcoder.bankservice.dto.email.EmailDTO;
import ru.duckcoder.bankservice.dto.email.EmailUpdateDTO;
import ru.duckcoder.bankservice.service.EmailService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/{userId}/emails")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailDTO create(
            @Valid @RequestBody EmailCreateDTO dto,
            @PathVariable Long userId) {
        return emailService.createWithUserId(dto, userId);
    }

    @PutMapping("/{userId}/emails/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody EmailUpdateDTO dto,
            @PathVariable Long userId) {
        return emailService.updateByIdWithUserId(id, dto, userId);
    }

    @DeleteMapping("/{userId}/emails/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @PathVariable Long userId) {
        emailService.deleteByIdWithUserId(id, userId);
    }
}