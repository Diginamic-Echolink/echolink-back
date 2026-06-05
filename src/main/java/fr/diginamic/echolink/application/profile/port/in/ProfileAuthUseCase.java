package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;

/**
 * Defines authentication use cases for profile registration and login.
 */
public interface ProfileAuthUseCase {

    /**
     * Registers a new profile using the provided authentication information.
     *
     * @param request authentication request containing registration data
     * @return authentication token generated for the registered profile
     * @throws InvalidCredentialsException if the provided data is invalid
     */
    String register(AuthRequest request) throws InvalidCredentialsException;

    /**
     * Authenticates a profile using the provided credentials.
     *
     * @param request authentication request containing login credentials
     * @return authentication token generated for the authenticated profile
     * @throws InvalidCredentialsException if the provided credentials are invalid
     */
    String login(AuthRequest request) throws InvalidCredentialsException;
}
