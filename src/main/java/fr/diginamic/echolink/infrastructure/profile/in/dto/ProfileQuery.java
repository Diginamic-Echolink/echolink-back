package fr.diginamic.echolink.infrastructure.profile.in.dto;

public record ProfileQuery(
        String firstName,
        String lastName,
        String pseudo,
        String email,
        String city,
        String codePostal,
        String address,
        String phoneNumber,
        String linkImgProfile) {
}
