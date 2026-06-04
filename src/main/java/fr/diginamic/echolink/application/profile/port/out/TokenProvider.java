package fr.diginamic.echolink.application.profile.port.out;

import fr.diginamic.echolink.domain.profile.Profile;

public interface TokenProvider {

    String generateToken(Profile profile);
}
