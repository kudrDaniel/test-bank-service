package ru.duckcoder.bankservice.controller.api;

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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                authDTO.getUsername(), authDTO.getPassword());
        authManager.authenticate(auth);

        return jwtUtils.generateToken(authDTO.getUsername());
    }
}
