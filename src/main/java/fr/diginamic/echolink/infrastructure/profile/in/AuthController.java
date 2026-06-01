package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileAuthUseCase;
import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.infrastructure.profile.in.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileAuthUseCase profileAuthUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(new AuthResponse(profileAuthUseCase.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(new AuthResponse(profileAuthUseCase.login(request)));
    }
}
