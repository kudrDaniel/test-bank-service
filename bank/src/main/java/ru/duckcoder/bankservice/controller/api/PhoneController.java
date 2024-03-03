package ru.duckcoder.bankservice.controller.api;

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
import ru.duckcoder.bankservice.dto.phone.PhoneCreateDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneUpdateDTO;
import ru.duckcoder.bankservice.service.PhoneService;

@Tag(name = "Phones", description = "The User API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class PhoneController {
    private final PhoneService phoneService;


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{userId}/phones")
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDTO create(
            @Valid @RequestBody PhoneCreateDTO dto,
            @PathVariable Long userId) {
        return phoneService.createWithUserId(dto, userId);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{userId}/phones/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhoneDTO update(
            @PathVariable Long id,
            @Valid @RequestBody PhoneUpdateDTO dto,
            @PathVariable Long userId) {
        return phoneService.updateByIdWithUserId(id, dto, userId);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{userId}/phones/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @PathVariable Long userId) {
        phoneService.deleteByIdWithUserId(id, userId);
    }
}
