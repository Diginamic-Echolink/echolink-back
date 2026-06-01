package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.Profile;

public interface ProfileUpdateUseCase {

    void update(Profile profile);
}
