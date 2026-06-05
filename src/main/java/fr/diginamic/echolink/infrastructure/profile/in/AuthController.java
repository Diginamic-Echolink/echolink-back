package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileAuthUseCase;
import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;
import fr.diginamic.echolink.infrastructure.profile.in.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing authentication endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request)
            throws InvalidCredentialsException {
        return ResponseEntity.ok(new AuthResponse(profileAuthUseCase.register(request)));
    }

    /**
     * Authenticates an existing profile.
     *
     * @param request authentication request containing login credentials
     * @return response containing the generated authentication token
     * @throws InvalidCredentialsException if the provided credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request)
            throws InvalidCredentialsException {
        return ResponseEntity.ok(new AuthResponse(profileAuthUseCase.login(request)));
    }
}
