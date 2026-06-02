package fr.diginamic.echolink.domain.location;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.domain.demography.Demography;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.domain.profile.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "location")
public class Location {

    /** Unique identifier of the Location. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Location's name */
    @Column(name = "name", nullable = false)
    private String name;

    /** Location's postal code */
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    /** Location's longitude */
    @Column(name = "longitude")
    private float longitude;

    /** Location's latitude */
    @Column(name = "latitude")
    private float latitude;

    /** Location's altitude */
    @Column(name = "altitude")
    private float altitude;

    /** Profile lived within this location. */
    @OneToMany(mappedBy="location")
    private Set<Profile> profiles = new HashSet<>();

    /** AirQuality link within this location. */
    @OneToMany(mappedBy="location")
    private List<AirQuality> airQualities = new ArrayList<>();

    /** Demography link within this location. */
    @OneToMany(mappedBy="location")
    private List<Demography> demographies = new ArrayList<>();

    /** Meteo link within this location. */
    @OneToMany(mappedBy="location")
    private List<Meteo> meteos = new ArrayList<>();

    /** Constructor for: Location */
    public Location() {}

    /**
     * Constructor for: Location
     *
     * @param postalCode
     * @param longitude
     * @param latitude
     */
    public Location(String postalCode, float longitude, float latitude) {
        this.postalCode = postalCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /** @return toString */
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", postalCode='" + postalCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", profiles=" + profiles +
                ", airQualities=" + airQualities +
                ", demographies=" + demographies +
                ", meteos=" + meteos +
                '}';
    }
}
