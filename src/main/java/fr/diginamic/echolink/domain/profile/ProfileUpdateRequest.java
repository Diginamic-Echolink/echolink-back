package fr.diginamic.echolink.domain.profile;

import jakarta.validation.constraints.Email;

public record ProfileUpdateRequest(
        String firstName,
        String lastName,
        String pseudo,
        @Email(message = "email need to be valid") String email,
        String password,
        String city,
        String postalCode,
        String address,
        String phoneNumber,
        String linkImgProfile
) {
}
