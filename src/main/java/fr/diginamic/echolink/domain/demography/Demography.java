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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "total_pop")
    private Long totalPop;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    public Demography() {}

    public Demography(Long totalPop, LocalDate timestamp) {
        this.totalPop = totalPop;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Demography{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", totalPop=" + totalPop +
                ", location=" + location +
                '}';
    }
}
