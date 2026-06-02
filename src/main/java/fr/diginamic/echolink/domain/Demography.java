package fr.diginamic.echolink.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Demography {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Long totalPop;

    @ManyToOne
    @JoinColumn(name="locationn_id")
    private Location location;

    public Demography() {
    }

    public Demography(UUID id, Long totalPop, Location location) {
        this.id = id;
        this.totalPop = totalPop;
        this.location = location;
    }

    public Demography(UUID idDemography, Long totalPop) {
        this. id = idDemography;
        this.totalPop = totalPop;
    }

    @Override
    public String toString() {
        return "Demography{" +
                "id=" + id +
                ", totalPop=" + totalPop +
                '}';
    }
}
