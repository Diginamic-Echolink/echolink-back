package fr.diginamic.echolink.domain.profile;

import java.util.UUID;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation2;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation3;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;

public final class ProfileTestData {

    public static Profile givenProfile1() {

        Profile profile = new Profile("test@mail.com", "password");
        profile.setId(givenUUID());
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setPseudo("jdoe");
        profile.setRole(ProfileRole.USER);
        profile.getFavoriteLocations().add(givenLocation1());
        return profile;
    }

    public static Profile givenProfile1(UUID id) {

        Profile profile = new Profile("test@mail.com", "password");
        profile.setId(id);
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setPseudo("jdoe");
        profile.setRole(ProfileRole.USER);
        profile.getFavoriteLocations().add(givenLocation1());
        return profile;
    }

    public static Profile givenProfile2() {

        Profile profile = new Profile("alice.martin@mail.com", "password123");
        profile.setId(givenUUID());
        profile.setFirstName("Alice");
        profile.setLastName("Martin");
        profile.setPseudo("amartin");
        profile.setRole(ProfileRole.USER);
        profile.setPostalCode("69000");
        profile.getFavoriteLocations().add(givenLocation2());
        return profile;
    }

    public static Profile givenProfile3() {

        Profile profile = new Profile("admin@echolink.fr", "adminPassword");
        profile.setId(givenUUID());
        profile.setId(givenUUID());
        profile.setFirstName("Robert");
        profile.setLastName("Durand");
        profile.setPseudo("admin");
        profile.setRole(ProfileRole.ADMIN);
        profile.setPostalCode("75001");
        profile.setPhoneNumber("0699999999");
        profile.getFavoriteLocations().add(givenLocation1());
        profile.getFavoriteLocations().add(givenLocation3());
        return profile;
    }

    public static ProfileUpdateRequest givenProfileUpdateRequest() {

        return new ProfileUpdateRequest(
                "Jane",
                "Smith",
                "jsmith",
                "jane@mail.com",
                "newPassword",
                "Lyon",
                "69000",
                "10 rue de Lyon",
                "0600000000",
                "image.jpg"
        );
    }

    public static ProfileUpdateRequest givenProfileUpdateRequestWithoutEmail() {

        return new ProfileUpdateRequest(
                "John",
                "Doe",
                "jdoe",
                null,
                "newPassword",
                "Lyon",
                "69000",
                "10 rue de Lyon",
                "0600000000",
                "image.jpg"
        );
    }

    public static ProfileUpdateRequest givenProfileUpdateRequestWithoutPassword() {

        return new ProfileUpdateRequest(
                "John",
                "Doe",
                "jdoe",
                "mail@test.fr",
                "",
                "city",
                "69000",
                "10 rue de Lyon",
                "0600000000",
                "image.jpg"
        );
    }

    public static ProfileUpdateRequest givenProfileUpdateRequestWithNullPassword() {

        return new ProfileUpdateRequest(
                "John",
                "Doe",
                "jdoe",
                "mail@test.fr",
                null,
                "city",
                "69000",
                "10 rue de Lyon",
                "0600000000",
                "image.jpg"
        );
    }

    public static AuthRequest givenAuthRequest() {

        return new AuthRequest(
                "newuser@mail.com",
                "password123"
        );
    }

    public static Profile givenProfileWithEmail(String email) {

        Profile profile = givenProfile1(givenUUID());
        profile.setEmail(email);
        return profile;
    }
}
