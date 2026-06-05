package fr.diginamic.echolink.infrastructure.common.in.dto;

import java.util.List;

public record ErrorMessageQuery(String message, List<FieldErrorQuery> fieldErrors) {}
