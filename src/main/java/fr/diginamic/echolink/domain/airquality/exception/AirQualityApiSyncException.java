package fr.diginamic.echolink.domain.airquality.exception;

public class AirQualityApiSyncException extends RuntimeException {

    public AirQualityApiSyncException(String message) {
        super(message);
    }

    public AirQualityApiSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
