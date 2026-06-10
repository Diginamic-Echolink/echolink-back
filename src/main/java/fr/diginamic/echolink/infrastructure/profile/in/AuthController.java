package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileAuthUseCase;
import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.profile.in.dto.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller exposing authentication endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and registration endpoints")
public class AuthController {

    /**
     * Use case responsible for profile authentication operations.
     */
    private final ProfileAuthUseCase profileAuthUseCase;

    /**
     * Registers a new profile.
     *
     * @param request authentication request containing registration data
     * @return response containing the generated authentication token
     * @throws InvalidCredentialsException if the provided data is invalid
     */
    @PostMapping("/register")
    @Operation(
            operationId = "register",
            summary = "Register a new user",
            description = "Creates a new user account and returns an authentication token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully registered",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid registration data",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRequest request
    ) throws InvalidCredentialsException {

        return ResponseEntity.status(CREATED).body(new AuthResponse(profileAuthUseCase.register(request)));
    }

    /**
     * Authenticates an existing profile.
     *
     * @param request authentication request containing login credentials
     * @return response containing the generated authentication token
     * @throws InvalidCredentialsException if the provided credentials are invalid
     */
    @PostMapping("/login")
    @Operation(
            operationId = "login",
            summary = "Authenticate user",
            description = "Authenticates user credentials and returns a JWT token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid credentials",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request
    ) throws InvalidCredentialsException {

        return ResponseEntity.ok(new AuthResponse(profileAuthUseCase.login(request)));
    }
}
