package fr.diginamic.echolink.application.profile.port.out;

import fr.diginamic.echolink.domain.profile.Profile;

public interface TokenService {

    String generateToken(Profile profile);
}
