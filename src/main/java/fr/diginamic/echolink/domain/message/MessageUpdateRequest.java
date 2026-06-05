package fr.diginamic.echolink.domain.message;

/**
 * Request object used to update an existing {@link Message}.
 * <p>
 * Only mutable fields of a message are included here. Fields that are null
 * or blank are ignored during the update process.
 *
 * @param text new content of the message
 */
public record MessageUpdateRequest(String text) {}
