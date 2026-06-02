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
@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile implements UserDetails {

    /**
     * Unique identifier of the profile.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * User's first name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * User's last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * User's public display name.
     */
    @Column(name = "pseudo")
    private String pseudo;

    /**
     * User's email address.
     * Must be unique within the application.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * User's city of residence.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * User's postal code.
     */
    @Column(name = "city")
    private String city;

    /**
     * User's postal code.
     */
    @Column(name = "postal_code")
    private String postalCode;

    /**
     * User's postal address.
     */
    @Column(name = "address")
    private String address;

    /**
     * User's phone number.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * URL of the user's profile picture.
     */
    @Column(name = "link_img_profile")
    private String linkImgProfile;

    /**
     * Role of the user.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;

    /**
     * Threads created by this user.
     */
    @OneToMany(mappedBy = "profile")
    private List<Thread> threads = new ArrayList<>();

    /**
     * Messages posted by this user.
     */
    @OneToMany(mappedBy = "profile")
    private List<Message> messages = new ArrayList<>();

    /**
     * Geographic location associated with the user profile.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Profile() {}

    public Profile(String email, String password) {
        this.email = email;
        this.password = password;
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

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdmin()
                ? List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"))
                : List.of(new SimpleGrantedAuthority("USER"));
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
                ", pseudonym='" + pseudo + '\'' +
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
