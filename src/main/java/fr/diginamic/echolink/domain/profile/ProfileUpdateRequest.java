package fr.diginamic.echolink.domain.profile;

public record ProfileUpdateRequest(
        String firstName,
        String lastName,
        String pseudo,
        String email,
        String password,
        String city,
        String postalCode,
        String address,
        String phoneNumber,
        String linkImgProfile
) {
}
