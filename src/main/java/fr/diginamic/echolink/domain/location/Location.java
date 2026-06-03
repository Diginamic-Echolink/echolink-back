package fr.diginamic.echolink.domain.location;

import fr.diginamic.echolink.domain.airquality.AirQuality;
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

    /** Location's commune code */
    @Column(name = "insee_code", unique = true, nullable = false)
    private String inseeCode;

    /** Location's postal code */
    @Column(name = "postal_code")
    private String postalCode;

    /** Location's longitude */
    @Column(name = "longitude")
    private double longitude;

    /** Location's latitude */
    @Column(name = "latitude")
    private double latitude;

    /** Location's population */
    @Column(name = "population")
    private long population;

    /** Profile lived within this location. */
    @OneToMany(mappedBy="location")
    private Set<Profile> profiles = new HashSet<>();

    /** AirQuality link within this location. */
    @OneToMany(mappedBy="location")
    private List<AirQuality> airQualities = new ArrayList<>();

    /** Meteo link within this location. */
    @OneToMany(mappedBy="location")
    private List<Meteo> meteos = new ArrayList<>();

    /** Constructor for: Location */
    public Location() {}

    public Location(
            String name,
            String inseeCode,
            String postalCode,
            double longitude,
            double latitude,
            long population) {
        this.name = name;
        this.inseeCode = inseeCode;
        this.postalCode = postalCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.population = population;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", postalCode='" + postalCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", population=" + population +
                ", profiles=" + profiles +
                ", airQualities=" + airQualities +
                ", meteos=" + meteos +
                '}';
    }
}
