package fr.diginamic.echolink.domain.meteo;

import fr.diginamic.echolink.domain.location.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Represents a weather record collected for a specific location.
 * Stores meteorological data such as temperature, atmospheric pressure,
 * humidity, wind information and rainfall measurements.
 */
@Getter
@Setter
@Entity
@Table(name = "meteo")
public class Meteo {

    /**
     * Unique identifier of the weather record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Date and time when the weather data was obtained from the external API.
     */
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    /**
     * Weather condition derived from WMO code.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "weather_condition")
    private WeatherCondition weatherCondition;

    /**
     * Measured temperature in °C.
     */
    @Column(name = "temperature")
    private float temperature;

    /**
     * Measured atmospheric pressure in hPa.
     */
    @Column(name = "atm_pressure")
    private float atmPressure;

    /**
     * Measured humidity level in %.
     */
    @Column(name = "humidity")
    private float humidity;

    /**
     * Measured wind speed in km/h.
     */
    @Column(name = "wind_speed")
    private float windSpeed;

    /**
     * Measured wind direction.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "wind_direction")
    private WindDirection windDirection;

    /**
     * Measured rainfall amount in mm.
     */
    @Column(name = "rain_fall")
    private float rainFall;

    /**
     * Location associated with this weather record.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Creates an empty weather record.
     * Required by JPA.
     */
    public Meteo() {}

    /**
     * Creates a weather record.
     *
     * @param recordedAt date and time when the weather data was recorded
     * @param weatherCondition weather condition
     * @param temperature measured temperature
     * @param atmPressure measured atmospheric pressure
     * @param humidity measured humidity level
     * @param windSpeed measured wind speed
     * @param windDirection measured wind direction
     * @param rainFall measured rainfall amount
     */
    public Meteo(
            LocalDateTime recordedAt,
            WeatherCondition weatherCondition,
            float temperature,
            float atmPressure,
            float humidity,
            float windSpeed,
            WindDirection windDirection,
            float rainFall) {
        this.recordedAt = recordedAt;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.atmPressure = atmPressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.rainFall = rainFall;
    }

    @Override
    public String toString() {
        return "Meteo{" +
                "id=" + id +
                ", recordedAt=" + recordedAt +
                ", weatherCondition=" + weatherCondition +
                ", temperature=" + temperature +
                ", atmPressure=" + atmPressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", rainFall=" + rainFall +
                ", location=" + location +
                '}';
    }
}
