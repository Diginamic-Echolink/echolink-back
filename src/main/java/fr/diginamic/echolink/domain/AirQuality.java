package fr.diginamic.echolink.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AirQuality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "particles_10")
    private int particles10;

    @Column(name = "particles_25")
    private int particles25;

    @Column(name = "eu_aqi")
    private int euAqi;

    @Column(name = "mono_carbon")
    private int monoCarbon;

    @Column(name = "ozone")
    private int ozone;

    @Column(name = "dust")
    private int dust;

    @Column(name = "nitrogen_dioxide")
    private int nitrogenDioxide;

    @Column(name = "sulfur_dioxide")
    private int sulfurDioxide;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    public AirQuality() {
    }

    public AirQuality(
            UUID id,
            LocalDateTime dateTime,
            int particles10,
            int particles25,
            int euAqi,
            int monoCarbon,
            int ozone,
            int dust,
            int nitrogenDioxide,
            int sulfurDioxide,
            Location location
    ) {
        this.id = id;
        this.dateTime = dateTime;
        this.particles10 = particles10;
        this.particles25 = particles25;
        this.euAqi = euAqi;
        this.monoCarbon = monoCarbon;
        this.ozone = ozone;
        this.dust = dust;
        this.nitrogenDioxide = nitrogenDioxide;
        this.sulfurDioxide = sulfurDioxide;
        this.location = location;
    }

    public AirQuality(
            UUID id,
            LocalDateTime dateTime,
            int particles10,
            int particles25,
            int euAqi,
            int monoCarbon,
            int ozone,
            int dust,
            int nitrogenDioxide,
            int sulfurDioxide
    ) {
        this.id = id;
        this.dateTime = dateTime;
        this.particles10 = particles10;
        this.particles25 = particles25;
        this.euAqi = euAqi;
        this.monoCarbon = monoCarbon;
        this.ozone = ozone;
        this.dust = dust;
        this.nitrogenDioxide = nitrogenDioxide;
        this.sulfurDioxide = sulfurDioxide;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", particles10=" + particles10 +
                ", particles25=" + particles25 +
                ", euAqi=" + euAqi +
                ", monoCarbon=" + monoCarbon +
                ", ozone=" + ozone +
                ", dust=" + dust +
                ", nitrogenDioxide=" + nitrogenDioxide +
                ", sulfurDioxide=" + sulfurDioxide +
                '}';
    }
}
