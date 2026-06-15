package fr.diginamic.echolink.infrastructure.profile.in.mapper;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.infrastructure.location.in.mapper.LocationQueryMapper;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Maps profile domain objects to profile query DTOs.
 */
@Component
@RequiredArgsConstructor
public class ProfileQueryMapper {

    /**
     * Mapper used to convert location domain objects into query DTOs.
     */
    private final LocationQueryMapper locationQueryMapper;

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
                profile.getPostalCode(),
                profile.getPhoneNumber(),
                profile.getLinkImgProfile(),
                profile.getRole().toString(),
                profile.getFavoriteLocations().stream()
                        .map(locationQueryMapper::toQuery)
                        .toList()
        );
    }
}
