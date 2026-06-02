package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.AuthRequest;
import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;

public interface ProfileAuthUseCase {

    String register(AuthRequest request) throws InvalidCredentialsException;

    String login(AuthRequest request) throws InvalidCredentialsException;
}
