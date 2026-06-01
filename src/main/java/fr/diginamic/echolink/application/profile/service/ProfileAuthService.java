package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileAuthUseCase;
import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;
import fr.diginamic.echolink.infrastructure.common.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileAuthService implements ProfileAuthUseCase {

    private final ProfileRepository repository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(AuthRequest request) {

        if (repository.getByEmail(request.email()).isPresent()) {
            throw new InvalidCredentialsException("Email already exists");
        }

        Profile profile = repository.create(new Profile(request.email(), passwordEncoder.encode(request.password())));
        return jwtService.generateToken(profile);
    }

    @Override
    public String login(AuthRequest request) throws UsernameNotFoundException {

        Profile profile = repository.getByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("User not found : " + request.email()));

        if (passwordEncoder.matches(request.password(), profile.getPassword())) {
            return jwtService.generateToken(profile);
        }

        throw new InvalidCredentialsException("Password incorrect for user : " + request.email());
    }
}
