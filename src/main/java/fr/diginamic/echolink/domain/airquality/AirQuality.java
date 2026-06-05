package fr.diginamic.echolink.domain.airquality;

import fr.diginamic.echolink.domain.location.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents air quality measurements recorded for a specific location.
 */
@Getter
@Setter
@Entity
@Table(name = "air_quality")
public class AirQuality {

    /** Unique identifier of the air quality record. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Date and time when the air quality data was recorded. */
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    /** Concentration of PM10 particles. */
    @Column(name = "particles_10")
    private float particles10;

    /** Concentration of PM2.5 particles. */
    @Column(name = "particles_25")
    private float particles25;

    /** European Air Quality Index value. */
    @Column(name = "eu_aqi")
    private byte euAqi;

    /** Carbon monoxide concentration. */
    @Column(name = "carbon_monoxide")
    private float carbonMonoxide;

    /** Ozone concentration. */
    @Column(name = "ozone")
    private float ozone;

    /** Dust concentration. */
    @Column(name = "dust")
    private float dust;

    /** Nitrogen dioxide concentration. */
    @Column(name = "nitrogen_dioxide")
    private float nitrogenDioxide;

    /** Sulfur dioxide concentration. */
    @Column(name = "sulfur_dioxide")
    private float sulfurDioxide;

    /** Geographic location associated with the air quality record. */
    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    /** Constructor for: AirQuality */
    public AirQuality() {}

    /**
     * Constructor for: AirQuality
     *
     * @param recordedAt date and time when the data was recorded
     * @param particles10 concentration of PM10 particles
     * @param particles25 concentration of PM2.5 particles
     * @param euAqi European Air Quality Index value
     * @param carbonMonoxide carbon monoxide concentration
     * @param ozone ozone concentration
     * @param dust dust concentration
     * @param nitrogenDioxide nitrogen dioxide concentration
     * @param sulfurDioxide sulfur dioxide concentration
     */
    public AirQuality(
            LocalDateTime recordedAt,
            float particles10,
            float particles25,
            byte euAqi,
            float carbonMonoxide,
            float ozone,
            float dust,
            float nitrogenDioxide,
            float sulfurDioxide) {
        this.recordedAt = recordedAt;
        this.particles10 = particles10;
        this.particles25 = particles25;
        this.euAqi = euAqi;
        this.carbonMonoxide = carbonMonoxide;
        this.ozone = ozone;
        this.dust = dust;
        this.nitrogenDioxide = nitrogenDioxide;
        this.sulfurDioxide = sulfurDioxide;
    }

    /**
     * Returns a string representation of the air quality record.
     *
     * @return string representation of the air quality record
     */
    @Override
    public String toString() {
        return "AirQuality{" +
                "id=" + id +
                ", recorded_at=" + recordedAt +
                ", particles10=" + particles10 +
                ", particles25=" + particles25 +
                ", euAqi=" + euAqi +
                ", carbonMonoxide=" + carbonMonoxide +
                ", ozone=" + ozone +
                ", dust=" + dust +
                ", nitrogenDioxide=" + nitrogenDioxide +
                ", sulfurDioxide=" + sulfurDioxide +
                ", location=" + location +
                '}';
    }
}
