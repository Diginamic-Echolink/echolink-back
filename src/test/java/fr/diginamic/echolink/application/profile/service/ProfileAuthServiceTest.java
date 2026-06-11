package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.application.profile.port.out.TokenProvider;
import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenAuthRequest;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfileWithEmail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileAuthServiceTest {

    @Mock
    private ProfileRepository repository;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ProfileAuthService service;

    @Test
    void should_register_new_user() throws InvalidCredentialsException {
        // GIVEN
        AuthRequest request = givenAuthRequest();

        Profile savedProfile = givenProfileWithEmail(request.email());

        when(repository.getByEmail(request.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encodedPassword");

        when(repository.save(any(Profile.class)))
                .thenReturn(savedProfile);

        when(tokenProvider.generateToken(savedProfile))
                .thenReturn("jwt-token");

        // WHEN
        String token = service.register(request);

        // THEN
        assertThat(token).isEqualTo("jwt-token");

        verify(passwordEncoder).encode("password123");
        verify(repository).save(any(Profile.class));
        verify(tokenProvider).generateToken(savedProfile);
    }

    @Test
    void should_throw_exception_when_registering_existing_email() {
        // GIVEN
        AuthRequest request = givenAuthRequest();

        when(repository.getByEmail(request.email()))
                .thenReturn(Optional.of(givenProfile1()));

        // WHEN / THEN
        assertThatThrownBy(() -> service.register(request))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Email already exists");

        verify(repository, never()).save(any());
        verify(tokenProvider, never()).generateToken(any());
    }

    @Test
    void should_login_user() throws InvalidCredentialsException {
        // GIVEN
        AuthRequest request = givenAuthRequest();

        Profile profile = givenProfile1();

        when(repository.getByEmail(request.email()))
                .thenReturn(Optional.of(profile));

        when(passwordEncoder.matches(
                request.password(),
                profile.getPassword()
        )).thenReturn(true);

        when(tokenProvider.generateToken(profile))
                .thenReturn("jwt-token");

        // WHEN
        String token = service.login(request);

        // THEN
        assertThat(token).isEqualTo("jwt-token");

        verify(tokenProvider).generateToken(profile);
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        // GIVEN
        AuthRequest request = givenAuthRequest();

        when(repository.getByEmail(request.email()))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("User not found : " + request.email());

        verify(tokenProvider, never()).generateToken(any());
    }

    @Test
    void should_throw_exception_when_password_is_incorrect() {
        // GIVEN
        AuthRequest request = givenAuthRequest();

        Profile profile = givenProfile1();

        when(repository.getByEmail(request.email()))
                .thenReturn(Optional.of(profile));

        when(passwordEncoder.matches(
                request.password(),
                profile.getPassword()
        )).thenReturn(false);

        // WHEN / THEN
        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Password incorrect for user : " + request.email());

        verify(tokenProvider, never()).generateToken(any());
    }

    @Test
    void should_create_profile_with_encoded_password() throws InvalidCredentialsException {
        // GIVEN
        AuthRequest request = givenAuthRequest();

        when(repository.getByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        when(repository.save(any(Profile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(tokenProvider.generateToken(any()))
                .thenReturn("jwt-token");

        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);

        // WHEN
        service.register(request);

        // THEN
        verify(repository).save(captor.capture());

        Profile saved = captor.getValue();

        assertThat(saved.getEmail()).isEqualTo(request.email());
        assertThat(saved.getPassword()).isEqualTo("encodedPassword");
    }
}
