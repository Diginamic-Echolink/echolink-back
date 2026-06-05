package fr.diginamic.echolink.application.profile.port.out;

import fr.diginamic.echolink.domain.profile.Profile;

/**
 * Defines the contract for generating authentication tokens.
 */
public interface TokenProvider {

    /**
     * Generates an authentication token for the specified profile.
     *
     * @param profile profile for which the token is generated
     * @return generated authentication token
     */
    String generateToken(Profile profile);
}
