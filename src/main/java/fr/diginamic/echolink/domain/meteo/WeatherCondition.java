package fr.diginamic.echolink.domain.meteo;

/**
 * Represents weather conditions based on WMO weather codes.
 */
public enum WeatherCondition {
    CLEAR_SKY,
    MAINLY_CLEAR,
    PARTLY_CLOUDY,
    OVERCAST,
    FOG,
    DRIZZLE,
    RAIN,
    SHOWERS,
    SNOW,
    SNOW_GRAINS,
    SNOW_SHOWERS,
    THUNDERSTORM,
    THUNDERSTORM_WITH_HAIL,
    UNKNOWN;

    /**
     * Converts a WMO weather code into the corresponding weather condition.
     *
     * @param code WMO weather code
     * @return corresponding weather condition
     */
    public static WeatherCondition fromWmoCode(int code) {
        return switch (code) {

            case 0 -> CLEAR_SKY;

            case 1 -> MAINLY_CLEAR;
            case 2 -> PARTLY_CLOUDY;
            case 3 -> OVERCAST;

            case 45, 48 -> FOG;

            case 51, 53, 55 -> DRIZZLE;
            case 61, 63, 65 -> RAIN;
            case 80, 81, 82 -> SHOWERS;

            case 71, 73, 75 -> SNOW;
            case 77 -> SNOW_GRAINS;
            case 85, 86 -> SNOW_SHOWERS;

            case 95 -> THUNDERSTORM;
            case 96, 99 -> THUNDERSTORM_WITH_HAIL;

            default -> UNKNOWN;
        };
    }
}
