package fr.diginamic.echolink.domain.profile;

import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.thread.Thread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user profile within the EchoLink application.
 * Contains personal information, contact details and user privileges.
 */
@Entity
@Getter
@Setter
public class Profile implements UserDetails {

    /**
     * Unique identifier of the profile.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /** User's first name */
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
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * User's city of residence.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * User's postal code.
     */
    private String city;


    /**
     * User's postal code.
     */
    private int postalCode;

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
     * Role of the user.
     */
    private ProfileRole role;

    /**
     * Threads created by this user.
     */
    @OneToMany(mappedBy = "profile")
    private final List<Thread> threads = new ArrayList<>();

    /**
     * Messages posted by this user.
     */
    @OneToMany(mappedBy = "profile")
    private final List<Message> messages = new ArrayList<>();

    /**
     * Geographic location associated with the user profile.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Profile() {}

    public Profile(String email, String password) {
        this.email = email;
        this.passwordHash = password;
        this.role = ProfileRole.USER;
    }

    public boolean isAdmin() {
        return role == ProfileRole.ADMIN;
    }

    @NullMarked
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdmin()
                ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
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
                ", role=" + role +
                '}';
    }
}
