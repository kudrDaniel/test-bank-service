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

@Tag(name = "Emails", description = "The Email API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "Returns created email", description = "Add email to user")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Email created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(name = "Email", implementation = EmailDTO.class)))
    })
    @PostMapping("/{userId}/emails")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailDTO create(
            @Valid @RequestBody EmailCreateDTO dto,
            @PathVariable Long userId) {
        return emailService.createWithUserId(dto, userId);
    }


    @Operation(summary = "Returns updated email", description = "Update email by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Email updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(name = "Email", implementation = EmailDTO.class)))
    })
    @PutMapping("/{userId}/emails/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody EmailUpdateDTO dto,
            @PathVariable Long userId) {
        return emailService.updateByIdWithUserId(id, dto, userId);
    }


    @Operation(summary = "No content", description = "Delete email by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Email deleted")
    })
    @DeleteMapping("/{userId}/emails/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @PathVariable Long userId) {
        emailService.deleteByIdWithUserId(id, userId);
    }
}
