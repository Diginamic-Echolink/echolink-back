package fr.diginamic.echolink.infrastructure.profile.in.dto;

/**
 * Represents profile information returned to clients.
 *
 * @param id unique identifier of the profile
 * @param firstName profile first name
 * @param lastName profile last name
 * @param pseudo profile pseudonym
 * @param email profile email address
 * @param city city of residence
 * @param codePostal postal code
 * @param address postal address
 * @param phoneNumber phone number
 * @param linkImgProfile link to the profile image
 */
public record ProfileQuery(
        String id,
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
