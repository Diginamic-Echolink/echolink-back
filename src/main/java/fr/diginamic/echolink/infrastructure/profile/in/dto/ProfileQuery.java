package fr.diginamic.echolink.infrastructure.profile.in.dto;

import fr.diginamic.echolink.infrastructure.location.in.dto.LocationQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Represents profile information returned to clients.
 *
 * @param id unique identifier of the profile
 * @param firstName profile first name
 * @param lastName profile last name
 * @param pseudo profile pseudonym
 * @param email profile email address
 * @param postalCode postal code
 * @param phoneNumber phone number
 * @param linkImgProfile link to the profile image
 * @param role profile role
 * @param favoriteLocations collection of favorite locations
 */
@Schema(description = "Profile information returned to the user")
public record ProfileQuery(

        @Schema(
                description = "Unique identifier of the profile",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        String id,

        @Schema(
                description = "First name of the profile",
                example = "John"
        )
        String firstName,

        @Schema(
                description = "Last name of the profile",
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
        String email,

        @Schema(
                description = "Postal code",
                example = "69000"
        )
        String postalCode,

        @Schema(
                description = "Phone number",
                example = "+33612345678"
        )
        String phoneNumber,

        @Schema(
                description = "URL of the profile image",
                example = "https://cdn.app.com/profiles/123.png"
        )
        String linkImgProfile,

        @Schema(
                description = "Role of this profile",
                example = "USER"
        )
        String role,

        @Schema(description = "Favorite locations of the profile")
        List<LocationQuery> favoriteLocations
) {
}
