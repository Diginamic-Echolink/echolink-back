package fr.diginamic.echolink.domain.demography;

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

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "demography")
public class Demography {

    /** Unique identifier of the Demography. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Demography's recorded at */
    @Column(name = "recorded_at")
    private LocalDate recordedAt;

    /** Demography's total_pop */
    @Column(name = "total_pop")
    private Long totalPop;

    /** Geographic location associated with the Demography. */
    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    /**
     * Constructor for: Demography
     */
    public Demography() {}

    /**
     * Constructor for: Demography
     *
     * @param totalPop
     * @param recordedAt
     */
    public Demography(Long totalPop, LocalDate recordedAt) {
        this.totalPop = totalPop;
        this.recordedAt = recordedAt;
    }

    /**
     * @return toString
     */
    @Override
    public String toString() {
        return "Demography{" +
                "id=" + id +
                ", recorded_at=" + recordedAt +
                ", totalPop=" + totalPop +
                ", location=" + location +
                '}';
    }
}
