package fr.diginamic.echolink.domain.shared.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalcUtils {

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
