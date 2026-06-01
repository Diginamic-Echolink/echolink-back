package fr.diginamic.echolink.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a user profile within the EchoLink application.
 * Contains personal information, contact details and user privileges.
 */
@Entity
@Getter
@Setter
public class Profile {

    /**
     * Unique identifier of the profile.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * User's first name.
     */
    private String firstName;

    /**
     * User's last name.
     */
    private String lastName;

    /**
     * User's public display name.
     */
    private String pseudonym;

    /**
     * User's email address.
     * Must be unique within the application.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * User's city of residence.
     */
    private String city;

    /**
     * User's postal code.
     */
    private String postalCode;

    /**
     * User's postal address.
     */
    private String address;

    /**
     * User's phone number.
     */
    private String phoneNumber;

    /**
     * URL of the user's profile picture.
     */
    private String linkImgProfile;

    /**
     * Indicates whether the user has administrator privileges.
     */
    private boolean admin;

    /**
     * Threads created by this user.
     */
    @OneToMany(mappedBy = "profile")
    private Set<Thread> threads;

    /**
     * Messages posted by this user.
     */
    @OneToMany(mappedBy = "profile")
    private Set<Message> messages;

    /**
     * Geographic location associated with the user profile.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Default constructor required by JPA.
     */
    public Profile() {}

    /**
     * Creates a user profile.
     *
     * @param id unique identifier of the profile
     * @param firstName user's first name
     * @param lastName user's last name
     * @param pseudonym user's display name
     * @param email user's email address
     * @param city user's city
     * @param postalCode user's postal code
     * @param address user's address
     * @param phoneNumber user's phone number
     * @param linkImgProfile URL of the profile picture
     * @param admin indicates whether the user has administrator privileges
     */
    public Profile(
            UUID id,
            String firstName,
            String lastName,
            String pseudonym,
            String email,
            String city,
            String postalCode,
            String address,
            String phoneNumber,
            String linkImgProfile,
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
        this.admin = admin;
    }

    /**
     * Returns a string representation of the profile.
     *
     * @return a string containing the profile information
     */
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pseudonym='" + pseudonym + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", linkImgProfile='" + linkImgProfile + '\'' +
                ", admin=" + admin +
                '}';
    }
}