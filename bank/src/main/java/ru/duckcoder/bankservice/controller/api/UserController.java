package ru.duckcoder.bankservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.duckcoder.bankservice.dto.user.UserCreateDTO;
import ru.duckcoder.bankservice.dto.user.UserDTO;
import ru.duckcoder.bankservice.dto.user.UserParamsDTO;
import ru.duckcoder.bankservice.service.UserService;

import java.util.List;

@Tag(name = "Users", description = "The User API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Returns list of users", description = "Returns users with received filter, pagination, sort")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users list returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(name = "User", implementation = UserDTO.class)))
            )
    })
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> index(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "direction", required = false) String direction,
            @RequestParam(name = "orderBy", required = false) String[] orderBy,
            @RequestParam(name = "params", required = false) UserParamsDTO params) {
        return userService.findAll(page, size, orderBy, direction, params);
    }

    @Operation(summary = "Returns user by id", description = "Get user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found and returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(name = "User", implementation = UserDTO.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Returns created user", description = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User is created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(name = "User", implementation = UserDTO.class)))
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO dto) {
        return userService.create(dto);
    }


    @Operation(summary = "No content", description = "Delete user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User deleted")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
