package ru.duckcoder.bankservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.duckcoder.bankservice.dto.AuthDTO;
import ru.duckcoder.bankservice.util.JwtUtils;

@Tag(name = "Authentications", description = "The Authentication API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    @Operation(summary = "Returns jwt token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "JWT Token created"
            )
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                authDTO.getUsername(), authDTO.getPassword());
        authManager.authenticate(auth);

        return jwtUtils.generateToken(authDTO.getUsername());
    }
}
