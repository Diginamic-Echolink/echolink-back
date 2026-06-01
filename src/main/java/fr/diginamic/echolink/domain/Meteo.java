package fr.diginamic.echolink.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a weather record collected for a specific location.
 * Stores meteorological data such as temperature, atmospheric pressure,
 * humidity, wind information and rainfall measurements.
 */
@Entity
@Getter
@Setter
public class Meteo {

    /**
     * Unique identifier of the weather record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Date and time when the weather data was recorded.
     */
    private LocalDateTime timestamp;

    /**
     * Measured temperature.
     */
    private int temp;

    /**
     * Measured atmospheric pressure.
     */
    private int atmPress;

    /**
     * Measured humidity level.
     */
    private int humidity;

    /**
     * Measured wind speed.
     */
    private int windSpeed;

    /**
     * Measured wind direction.
     */
    private int windDir;

    /**
     * Measured rainfall amount.
     */
    private int rain;

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
     * @param id unique identifier of the weather record
     * @param timestamp date and time when the weather data was recorded
     * @param temp measured temperature
     * @param atmPress measured atmospheric pressure
     * @param humidity measured humidity level
     * @param windSpeed measured wind speed
     * @param windDir measured wind direction
     * @param rain measured rainfall amount
     */
    public Meteo(
            UUID id,
            LocalDateTime timestamp,
            int temp,
            int atmPress,
            int humidity,
            int windSpeed,
            int windDir,
            int rain) {
        this.id = id;
        this.timestamp = timestamp;
        this.temp = temp;
        this.atmPress = atmPress;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.rain = rain;
    }

    /**
     * Returns a string representation of the weather record.
     *
     * @return a string containing the weather data
     */
    @Override
    public String toString() {
        return "Meteo{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", temp=" + temp +
                ", atmPress=" + atmPress +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDir=" + windDir +
                ", rain=" + rain +
                '}';
    }
}