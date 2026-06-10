package fr.diginamic.echolink.domain.profile;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Profile update request payload")
public record ProfileUpdateRequest(

        @Schema(
                description = "First name",
                example = "John"
        )
        String firstName,

        @Schema(
                description = "Last name",
                example = "Doe"
        )
        String lastName,

        @Schema(
                description = "User pseudonym",
                example = "john_doe"
        )
        String pseudo,

        @Schema(
                description = "Email address",
                example = "john.doe@email.com"
        )
        @Email(message = "email need to be valid")
        String email,


        @Schema(
                description = "Password (if updated)",
                example = "Str0ngP@ssword",
                accessMode = Schema.AccessMode.WRITE_ONLY
        )
        String password,

        @Schema(
                description = "City of residence",
                example = "Lyon"
        )
        String city,

        @Schema(
                description = "Postal code",
                example = "69000"
        )
        String postalCode,

        @Schema(
                description = "Full postal address",
                example = "12 rue de la Paix"
        )
        String address,


        @Schema(
                description = "Phone number",
                example = "+33612345678"
        )
        String phoneNumber,

        @Schema(
                description = "Profile image URL",
                example = "https://cdn.app.com/profiles/123.png"
        )
        String linkImgProfile
) {
}
