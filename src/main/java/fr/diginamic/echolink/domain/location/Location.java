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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "altitude")
    private float altitude;

    @OneToMany(mappedBy="location")
    private final Set<Profile> profiles = new HashSet<>();

    @OneToMany(mappedBy="location")
    private final List<AirQuality> airQualities = new ArrayList<>();

    @OneToMany(mappedBy="location")
    private final List<Demography> demographies = new ArrayList<>();

    @OneToMany(mappedBy="location")
    private final List<Meteo> meteos = new ArrayList<>();

    public Location() {}

    public Location(String postalCode, float longitude, float latitude) {
        this.postalCode = postalCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

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
