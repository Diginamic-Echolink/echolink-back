package fr.diginamic.echolink.domain.meteo;

import java.time.LocalDateTime;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation2;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation3;

public final class MeteoTestData {

    public static Meteo givenMeteo1() {

        Meteo meteo = new Meteo(
                LocalDateTime.now(),
                WeatherCondition.CLEAR_SKY,
                22.5f,
                1013.2f,
                55.0f,
                12.0f,
                WindDirection.NORTH,
                0.0f
        );
        meteo.setLocation(givenLocation1());
        return meteo;
    }

    public static Meteo givenMeteo2() {

        Meteo meteo = new Meteo(
                LocalDateTime.now().minusHours(3),
                WeatherCondition.RAIN,
                12.3f,
                1005.8f,
                88.0f,
                35.0f,
                WindDirection.SOUTH_WEST,
                14.5f
        );
        meteo.setLocation(givenLocation2());
        return meteo;
    }

    public static Meteo givenMeteo3() {

        Meteo meteo = new Meteo(
                LocalDateTime.now().minusDays(1),
                WeatherCondition.SNOW,
                -4.2f,
                1025.4f,
                92.0f,
                18.0f,
                WindDirection.EAST,
                6.8f
        );
        meteo.setLocation(givenLocation3());
        return meteo;
    }
}
