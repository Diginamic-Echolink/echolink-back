package fr.diginamic.echolink.domain.shared.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalcUtils {

    /** Constant for the conversion of 1° to km */
    private static final double DEGRE_TO_KM_CONVERSION = 111.11;

    public static float averageFloat(List<Float> values) {
        if (values == null || values.isEmpty()) return 0f;

        double avg = values.stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0d);

        return roundToFloatWithOneDecimal(avg);
    }

    public static int averageInteger(List<Integer> values) {
        if (values == null || values.isEmpty()) return 0;

        double avg = values.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0d);

        return roundToIntegerWithOneDecimal(avg);
    }

    public static byte maxByte(List<Byte> values) {
        if (values == null || values.isEmpty()) return 0;

        double max =  values.stream()
                .mapToDouble(Byte::doubleValue)
                .max()
                .orElse(0d);

        return (byte) Math.round(max);
    }

    public static double deltaLatitudeToKm(double delta) {
        return delta / DEGRE_TO_KM_CONVERSION;
    }

    public static double deltaLongitudeToKm(double latitude, int delta) {

        // 1° ~= 111km * cos(latitude°)
        double latitudeRad = Math.toRadians(latitude);

        // Calculating the distance in km
        double latitudeCoefficient = Math.cos(latitudeRad);
        return delta / (DEGRE_TO_KM_CONVERSION * latitudeCoefficient);
    }

    private static float roundToFloatWithOneDecimal(double value) {
        return BigDecimal.valueOf(value)
                .setScale(1, RoundingMode.HALF_UP)
                .floatValue();
    }

    private static int roundToIntegerWithOneDecimal(double value) {
        return BigDecimal.valueOf(value)
                .setScale(1, RoundingMode.HALF_UP)
                .intValue();
    }
}
