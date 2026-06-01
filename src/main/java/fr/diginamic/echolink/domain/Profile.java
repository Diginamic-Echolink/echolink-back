package fr.diginamic.echolink.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private String pseudonym;

    private String email;

    private String city;

    private int postalCode;

    private String address;

    private String phoneNumber;

    private String linkImgProfile;

    private long meteoBookmarks;

    private boolean admin;

    public Profile() {}

    public Profile(
            UUID id,
            String firstName,
            String lastName,
            String pseudonym,
            String email,
            String city,
            int postalCode,
            String address,
            String phoneNumber,
            String linkImgProfile,
            long meteoBookmarks,
            boolean admin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudonym = pseudonym;
        this.email = email;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.linkImgProfile = linkImgProfile;
        this.meteoBookmarks = meteoBookmarks;
        this.admin = admin;
    }

}
