package fr.diginamic.echolink.application.profile.port.in;

import java.util.UUID;

public interface ProfileDeleteUseCase {

    void delete(UUID id);
}
