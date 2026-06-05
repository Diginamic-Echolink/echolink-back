package fr.diginamic.echolink.domain.profile;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation2;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation3;

public final class ProfileTestData {

    public static Profile givenProfile1() {

        Profile profile = new Profile("test@mail.com", "password");
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setPseudo("jdoe");
        profile.setRole(ProfileRole.USER);
        profile.setLocation(givenLocation1());
        return profile;
    }

    public static Profile givenProfile2() {

        Profile profile = new Profile("alice.martin@mail.com", "password123");
        profile.setFirstName("Alice");
        profile.setLastName("Martin");
        profile.setPseudo("amartin");
        profile.setRole(ProfileRole.USER);
        profile.setCity("Lyon");
        profile.setPostalCode("69000");
        profile.setPhoneNumber("0612345678");
        profile.setLocation(givenLocation2());
        return profile;
    }

    public static Profile givenProfile3() {

        Profile profile = new Profile("admin@echolink.fr", "adminPassword");
        profile.setFirstName("Robert");
        profile.setLastName("Durand");
        profile.setPseudo("admin");
        profile.setRole(ProfileRole.ADMIN);
        profile.setCity("Paris");
        profile.setPostalCode("75001");
        profile.setPhoneNumber("0699999999");
        profile.setLocation(givenLocation3());
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

    public static AuthRequest givenAuthRequest() {

        return new AuthRequest(
                "newuser@mail.com",
                "password123"
        );
    }

    public static Profile givenProfileWithEmail(String email) {

        Profile profile = givenProfile1();
        profile.setEmail(email);
        return profile;
    }
}
