package fr.diginamic.echolink.infrastructure.airquality.out.mapper;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import fr.diginamic.echolink.infrastructure.airquality.out.dto.OpenMeteoAirQualityResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.averageFloat;
import static fr.diginamic.echolink.domain.shared.utils.CalcUtils.maxByte;

@Component
public class OpenMeteoAirQualityResponseMapper {

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
