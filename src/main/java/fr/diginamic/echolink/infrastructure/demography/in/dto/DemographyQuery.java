package fr.diginamic.echolink.infrastructure.demography.in.dto;

import java.time.LocalDate;

public record DemographyQuery(
        LocalDate recordedAt,
        Long totalPop) {
}

