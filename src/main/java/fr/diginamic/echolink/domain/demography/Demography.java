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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recorded_at")
    private LocalDate recordedAt;

    @Column(name = "total_pop")
    private Long totalPop;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    public Demography() {}

    public Demography(Long totalPop, LocalDate recordedAt) {
        this.totalPop = totalPop;
        this.recordedAt = recordedAt;
    }

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
