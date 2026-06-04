package fr.diginamic.echolink.infrastructure.airquality.out.mapper;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.out.dto.OpenMeteoAirQualityResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OpenMeteoAirQualityResponseMapper {

    public AirQuality toAirQuality(OpenMeteoAirQualityResponse response) {

        OpenMeteoAirQualityResponse.Hourly hourlyDatas = response.hourly();

        return new AirQuality(
                LocalDateTime.now(),
                average(hourlyDatas.pm10()),
                average(hourlyDatas.pm25()),
                averageByte(hourlyDatas.euAqi()),
                average(hourlyDatas.carbonMonoxide()),
                average(hourlyDatas.ozone()),
                average(hourlyDatas.dust()),
                average(hourlyDatas.nitrogenDioxide()),
                average(hourlyDatas.sulfurDioxide())
        );
    }

    private float average(List<Float> values) {
        if (values == null || values.isEmpty()) return 0f;

        double avg = values.stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0d);

        return roundToOneDecimal(avg);
    }

    private byte averageByte(List<Byte> values) {
        if (values == null || values.isEmpty()) return 0;

        double avg = values.stream()
                .mapToInt(Byte::intValue)
                .average()
                .orElse(0d);

        return (byte) Math.round(avg);
    }

    private float roundToOneDecimal(double value) {
        return BigDecimal.valueOf(value)
                .setScale(1, RoundingMode.HALF_UP)
                .floatValue();
    }
}
