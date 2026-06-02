package fr.diginamic.echolink.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "postal_code", nullable = false)
    private int postCode;

    @Column(name = "longitude", nullable = false)
    private int longitude;

    @Column(name = "latitude", nullable = false)
    private int latitude;

    @OneToMany(mappedBy="profile")
    private Set<Profile> profiles = new HashSet<>();

    @OneToMany(mappedBy="airQualty")
    private Set<AirQuality> airQualities = new HashSet<>();

    @OneToMany(mappedBy="demography")
    private Set<Demography> demographies = new HashSet<>();

    @OneToMany(mappedBy="meteo")
    private Set<Meteo> meteos = new HashSet<>();



    @ManyToOne
    @JoinColumn(name="id_demography")
    private Demography demography;

    @ManyToOne
    @JoinColumn(name="id_location")
    private Location location;

    public Location() {
    }

    public Location(UUID id, int postCode, int longitude, int latitude) {
        this.id = id;
        this.postCode = postCode;
        this.longitude = longitude;
        this.latitude = latitude;;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", postCode=" + postCode +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
