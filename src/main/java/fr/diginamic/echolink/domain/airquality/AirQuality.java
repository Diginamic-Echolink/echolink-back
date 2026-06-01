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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "particles_10")
    private float particles10;

    @Column(name = "particles_25")
    private float particles25;

    @Column(name = "eu_aqi")
    private byte euAqi;

    @Column(name = "carbon_monoxide")
    private float carbonMonoxide;

    @Column(name = "ozone")
    private float ozone;

    @Column(name = "dust")
    private float dust;

    @Column(name = "nitrogen_dioxide")
    private float nitrogenDioxide;

    @Column(name = "sulfur_dioxide")
    private float sulfurDioxide;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    public AirQuality() {}

    public AirQuality(
            LocalDateTime timestamp,
            float particles10,
            float particles25,
            byte euAqi,
            float carbonMonoxide,
            float ozone,
            float dust,
            float nitrogenDioxide,
            float sulfurDioxide) {
        this.timestamp = timestamp;
        this.particles10 = particles10;
        this.particles25 = particles25;
        this.euAqi = euAqi;
        this.carbonMonoxide = carbonMonoxide;
        this.ozone = ozone;
        this.dust = dust;
        this.nitrogenDioxide = nitrogenDioxide;
        this.sulfurDioxide = sulfurDioxide;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "id=" + id +
                ", timestamp=" + timestamp +
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
