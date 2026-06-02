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

@Getter
@Setter
@Entity
@Table(name = "air_quality")
public class AirQuality {

    /** Unique identifier of the AirQuality. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** AirQuality's recorded at */
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    /** AirQuality's particles 10 */
    @Column(name = "particles_10")
    private float particles10;

    /** AirQuality's particles 25 */
    @Column(name = "particles_25")
    private float particles25;

    /** AirQuality's EU AQI */
    @Column(name = "eu_aqi")
    private byte euAqi;

    /** AirQuality's carbon monoxide */
    @Column(name = "carbon_monoxide")
    private float carbonMonoxide;

    /** AirQuality's ozone */
    @Column(name = "ozone")
    private float ozone;

    /** AirQuality's dust */
    @Column(name = "dust")
    private float dust;

    /** AirQuality's nitrogen dioxide */
    @Column(name = "nitrogen_dioxide")
    private float nitrogenDioxide;

    /** AirQuality's sulfure dioxide */
    @Column(name = "sulfur_dioxide")
    private float sulfurDioxide;

    /** Geographic location associated with the AirQuality */
    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    /** Constructor for: AirQuality */
    public AirQuality() {}

    /**
     * Constructor for: AirQuality
     *
     * @param recordedAt
     * @param particles10
     * @param particles25
     * @param euAqi
     * @param carbonMonoxide
     * @param ozone
     * @param dust
     * @param nitrogenDioxide
     * @param sulfurDioxide
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

    /** @return toString */
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
