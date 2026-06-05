package fr.diginamic.echolink.infrastructure.profile.in.dto;

/**
 * Represents the response returned after a successful authentication.
 *
 * @param token generated authentication token
 */
public record AuthResponse(String token) {}
