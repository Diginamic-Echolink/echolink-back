package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.AuthRequest;

public interface ProfileAuthUseCase {

    String register(AuthRequest request);

    String login(AuthRequest request);
}
