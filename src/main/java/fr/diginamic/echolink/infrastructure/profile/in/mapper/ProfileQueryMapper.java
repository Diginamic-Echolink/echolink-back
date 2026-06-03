package fr.diginamic.echolink.infrastructure.profile.in.mapper;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import org.springframework.stereotype.Component;

@Component
public class ProfileQueryMapper {

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
