package fr.diginamic.echolink.domain.profile;

import jakarta.validation.constraints.Email;

/**
 * Represents the information used to update a profile.
 *
 * @param firstName profile first name
 * @param lastName profile last name
 * @param pseudo profile pseudonym
 * @param email profile email address
 * @param password profile password
 * @param city city of residence
 * @param postalCode postal code
 * @param address postal address
 * @param phoneNumber phone number
 * @param linkImgProfile link to the profile image
 */
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
