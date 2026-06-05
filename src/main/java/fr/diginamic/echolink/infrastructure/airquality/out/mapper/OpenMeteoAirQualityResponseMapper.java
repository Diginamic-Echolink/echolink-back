package fr.diginamic.echolink.infrastructure.airquality.out.mapper;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.out.dto.OpenMeteoAirQualityResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.averageFloat;
import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.maxByte;

/**
 * Mapper responsible for converting Open-Meteo air quality API responses
 * into {@link AirQuality} domain objects.
 */
@Component
public class OpenMeteoAirQualityResponseMapper {

    /**
     * Converts an Open-Meteo air quality response into an air quality domain object.
     *
     * @param dto response returned by the Open-Meteo Air Quality API
     * @return the corresponding air quality domain object
     */
    public AirQuality toAirQuality(OpenMeteoAirQualityResponse dto) {

        OpenMeteoAirQualityResponse.Hourly hourlyDatas = dto.hourly();

        return new AirQuality(
                LocalDateTime.now(),
                averageFloat(hourlyDatas.pm10()),
                averageFloat(hourlyDatas.pm25()),
                maxByte(hourlyDatas.euAqi()),
                averageFloat(hourlyDatas.carbonMonoxide()),
                averageFloat(hourlyDatas.ozone()),
                averageFloat(hourlyDatas.dust()),
                averageFloat(hourlyDatas.nitrogenDioxide()),
                averageFloat(hourlyDatas.sulfurDioxide())
        );
    }
}