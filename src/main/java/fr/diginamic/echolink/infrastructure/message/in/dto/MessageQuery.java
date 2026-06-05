package fr.diginamic.echolink.infrastructure.message.in.dto;

/**
 * DTO representing a message returned by the API.
 * <p>
 * This object is used to expose message data to clients without
 * exposing the internal domain model.
 *
 * @param id unique identifier of the message
 * @param text content of the message
 * @param profileId identifier of the author profile
 * @param threadId identifier of the thread containing the message
 */
public record MessageQuery(
    String id,
    String text,
    String profileId,
    String threadId) {
}
