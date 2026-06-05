package fr.diginamic.echolink.infrastructure.profile.in.mapper;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import org.springframework.stereotype.Component;

/**
 * Maps profile domain objects to profile query DTOs.
 */
@Component
public class ProfileQueryMapper {

    /**
     * Converts a profile domain object into a profile query DTO.
     *
     * @param profile profile domain object to convert
     * @return corresponding profile query DTO
     */
    public ProfileQuery toQuery(Profile profile) {

        return new ProfileQuery(
                profile.getId().toString(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPseudo(),
                profile.getEmail(),
                profile.getCity(),
                profile.getPostalCode(),
                profile.getAddress(),
                profile.getPhoneNumber(),
                profile.getLinkImgProfile()
        );
    }
}
